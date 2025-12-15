package com.johnmartin.auth.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.johnmartin.auth.constants.api.ApiErrorMessages;
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

@Service
public class AuthService {

    private static final String DEBUG_TAG = AuthService.class.getSimpleName();

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Register a user
     * 
     * @param request
     *            - RegisterRequest
     * @return AuthResponse
     */
    public AuthResponse register(RegisterRequest request) {
        LoggerUtility.d(DEBUG_TAG, String.format("Execute method: [register] request: [%s]", request));

        if (request == null) {
            throw new BadRequestException(ApiErrorMessages.INVALID_REQUEST);
        }

        // TODO: add validations for other fields

        if (StringUtils.isBlank(request.getEmail()) || StringUtils.isBlank(request.getPassword())) {
            throw new BadRequestException(ApiErrorMessages.EMAIL_AND_PASSWORD_ARE_REQUIRED);
        }

        if (userService.findByEmail(request.getEmail()).isPresent()) {
            throw new ConflictException(ApiErrorMessages.USER_WITH_THIS_EMAIL_ALREADY_EXISTS);
        }

        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setProfileImageUrl(request.getProfileImageUrl());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        UserEntity createdUser = userService.createUser(user);

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
        LoggerUtility.d(DEBUG_TAG, String.format("Execute method: [login] request: [%s]", request));

        if (request == null) {
            throw new BadRequestException(ApiErrorMessages.INVALID_REQUEST);
        }

        if (StringUtils.isBlank(request.getEmail()) || StringUtils.isBlank(request.getPassword())) {
            throw new BadRequestException(ApiErrorMessages.EMAIL_AND_PASSWORD_ARE_REQUIRED);
        }

        UserEntity user = userService.findByEmail(request.getEmail())
                                     .orElseThrow(() -> new UnauthorizedException(ApiErrorMessages.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException(ApiErrorMessages.INVALID_CREDENTIALS);
        }

        String token = jwtUtil.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserResponse(UserMapper.toResponse(user));
        return response;
    }
}
