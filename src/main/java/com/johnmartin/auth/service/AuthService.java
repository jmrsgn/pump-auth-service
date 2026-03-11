package com.johnmartin.auth.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.johnmartin.auth.constants.SecurityConstants;
import com.johnmartin.auth.constants.api.ApiErrorMessages;
import com.johnmartin.auth.dto.request.CreateSocialUserRequest;
import com.johnmartin.auth.dto.request.LoginRequest;
import com.johnmartin.auth.dto.request.RegisterRequest;
import com.johnmartin.auth.dto.response.AuthResponse;
import com.johnmartin.auth.entities.UserEntity;
import com.johnmartin.auth.exception.BadRequestException;
import com.johnmartin.auth.exception.ConflictException;
import com.johnmartin.auth.exception.UnauthorizedException;
import com.johnmartin.auth.mapper.UserMapper;
import com.johnmartin.auth.security.JwtUtil;
import com.johnmartin.auth.utilities.LoggerUtility;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

    private final Class<AuthService> clazz = AuthService.class;

    private final UserService userService;

    private final SocialServiceClient socialServiceClient;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService,
                       SocialServiceClient socialServiceClient,
                       JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.socialServiceClient = socialServiceClient;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a user
     * 
     * @param request
     *            - RegisterRequest
     * @return AuthResponse
     */
    public AuthResponse register(HttpServletRequest request, RegisterRequest registerRequest) {
        LoggerUtility.d(clazz, String.format("Execute method: [register] request: [%s]", request));

        if (request == null) {
            throw new BadRequestException(ApiErrorMessages.INVALID_REQUEST);
        }

        // TODO: add validations for other fields

        if (StringUtils.isBlank(registerRequest.getEmail()) || StringUtils.isBlank(registerRequest.getPassword())) {
            throw new BadRequestException(ApiErrorMessages.EMAIL_AND_PASSWORD_ARE_REQUIRED);
        }

        if (userService.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new ConflictException(ApiErrorMessages.USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
        }

        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(Boolean.TRUE);

        UserEntity createdUser = userService.createUser(user);

        // Create Social User
        CreateSocialUserRequest createSocialUserRequest = new CreateSocialUserRequest();
        createSocialUserRequest.setId(createdUser.getId().toString());
        createSocialUserRequest.setFirstName(createdUser.getFirstName());
        createSocialUserRequest.setLastName(createdUser.getLastName());
        createSocialUserRequest.setEmail(createdUser.getEmail());

        String requestId = (String) request.getAttribute(SecurityConstants.REQUEST_ID);

        // TODO: add checking of auth header
        socialServiceClient.createUser(requestId, createSocialUserRequest);

        AuthResponse response = new AuthResponse();
        response.setUserResponse(UserMapper.toResponse(createdUser));
        return response;
    }

    /**
     * Login a user
     * 
     * @param request
     *            - LoginRequest
     * @return AuthResponse
     */
    public AuthResponse login(LoginRequest request) {
        LoggerUtility.d(clazz, String.format("Execute method: [login] request: [%s]", request));

        if (request == null) {
            throw new BadRequestException(ApiErrorMessages.INVALID_REQUEST);
        }

        if (StringUtils.isBlank(request.getEmail()) || StringUtils.isBlank(request.getPassword())) {
            throw new BadRequestException(ApiErrorMessages.EMAIL_AND_PASSWORD_ARE_REQUIRED);
        }

        UserEntity user = userService.findByEmail(request.getEmail())
                                     .orElseThrow(() -> new UnauthorizedException(ApiErrorMessages.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException(ApiErrorMessages.INVALID_CREDENTIALS);
        }

        String token = jwtUtil.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserResponse(UserMapper.toResponse(user));
        return response;
    }
}
