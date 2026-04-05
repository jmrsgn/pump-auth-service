package com.johnmartin.auth.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.johnmartin.auth.constants.SecurityConstants;
import com.johnmartin.auth.constants.view.ViewAttributes;
import com.johnmartin.auth.dto.request.CreateSocialUserRequest;
import com.johnmartin.auth.dto.request.LoginRequest;
import com.johnmartin.auth.dto.request.RegisterRequest;
import com.johnmartin.auth.dto.response.AuthResponse;
import com.johnmartin.auth.entity.RoleEntity;
import com.johnmartin.auth.entity.UserEntity;
import com.johnmartin.auth.enums.VerificationStatus;
import com.johnmartin.auth.events.AuthUserCreatedEvent;
import com.johnmartin.auth.exception.*;
import com.johnmartin.auth.mapper.UserMapper;
import com.johnmartin.auth.security.JwtUtil;
import com.johnmartin.auth.service.client.SocialServiceClient;
import com.johnmartin.auth.utilities.LogMaskUtility;
import com.johnmartin.auth.utilities.LoggerUtility;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    private static final Class<AuthService> clazz = AuthService.class;

    private final UserService userService;

    private final RoleService roleService;

    private final SocialServiceClient socialServiceClient;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    public AuthService(UserService userService,
                       RoleService roleService,
                       SocialServiceClient socialServiceClient,
                       JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder,
                       ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.roleService = roleService;
        this.socialServiceClient = socialServiceClient;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Register a user
     * 
     * @param request
     *            - RegisterRequest
     * @return AuthResponse
     */
    public AuthResponse register(HttpServletRequest request, RegisterRequest registerRequest) {
        LoggerUtility.d(clazz, String.format("Execute method: [register] request: [%s]", LogMaskUtility.mask(request)));

        if (request == null) {
            throw new BadRequestException("Invalid request");
        }

        // Check for user email duplicates
        if (userService.findOptionalByEmail(registerRequest.email()).isPresent()) {
            throw new ConflictException("User with this email already exists");
        }

        LoggerUtility.d(clazz, "Creating user");

        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setEmail(registerRequest.email());
        user.setPhone(registerRequest.phone());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.password()));
        user.setEnabled(Boolean.FALSE); // Users must activate first the account

        RoleEntity userRole = roleService.getRole(registerRequest.role())
                                         .orElseThrow(() -> new NotFoundException("Invalid role"));

        user.getRoles().add(userRole);

        // Save user to DB
        UserEntity createdUser = userService.createUser(user);
        LoggerUtility.d(clazz, "created user: [%s]");

        // Send user email verification
        applicationEventPublisher.publishEvent(new AuthUserCreatedEvent(createdUser.getId(), createdUser.getEmail()));

        // Create Social User
        CreateSocialUserRequest createSocialUserRequest = new CreateSocialUserRequest(createdUser.getId().toString(),
                                                                                      createdUser.getFirstName(),
                                                                                      createdUser.getLastName(),
                                                                                      createdUser.getEmail());

        String requestId = (String) request.getAttribute(SecurityConstants.REQUEST_ID);

        try {
            socialServiceClient.createUser(requestId, createSocialUserRequest);
            LoggerUtility.d(clazz, "Social user created");
        } catch (Exception e) {
            // When creating of social user fails, delete auth user
            LoggerUtility.e(clazz, e.getMessage(), e);
            userService.deleteById(createdUser.getId());
        }

        return new AuthResponse(null, UserMapper.toResponse(createdUser), null);
    }

    /**
     * Login a user
     * 
     * @param request
     *            - LoginRequest
     * @return AuthResponse
     */
    public AuthResponse login(LoginRequest request) {
        LoggerUtility.d(clazz, String.format("Execute method: [login] request: [%s]", LogMaskUtility.mask(request)));

        if (request == null) {
            throw new BadRequestException("Invalid request");
        }

        if (StringUtils.isBlank(request.email()) || StringUtils.isBlank(request.password())) {
            throw new BadRequestException("Email and password are required");
        }

        UserEntity user = userService.findByEmail(request.email());

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        if (Boolean.FALSE.equals(user.getEnabled())) {
            throw new ForbiddenException("User account is not yet activated");
        }

        String token = jwtUtil.generateToken(user.getId().toString());
        LoggerUtility.d(clazz, "Token created");
        return new AuthResponse(token, UserMapper.toResponse(user), jwtUtil.getExpirationSeconds());
    }

    /**
     * Set model data
     * 
     * @param model
     *            - Thymeleaf model container data for HTML
     * @param status
     *            - Verification status
     */
    public void setModelData(Model model, VerificationStatus status) {
        LoggerUtility.d(clazz, "Execute method: [setModelData]");
        if (status == VerificationStatus.ALREADY_VERIFIED) {
            model.addAttribute(ViewAttributes.TITLE, "Already Verified");
            model.addAttribute(ViewAttributes.MESSAGE, "Your account is already verified.");
        } else {
            model.addAttribute(ViewAttributes.TITLE, "Success!");
            model.addAttribute(ViewAttributes.MESSAGE, "Your account has been successfully verified.");
        }
    }
}
