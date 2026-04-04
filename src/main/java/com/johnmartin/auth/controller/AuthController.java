package com.johnmartin.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.constants.api.messages.ApiMessages;
import com.johnmartin.auth.constants.api.messages.UserMessages;
import com.johnmartin.auth.dto.request.LoginRequest;
import com.johnmartin.auth.dto.request.RegisterRequest;
import com.johnmartin.auth.dto.response.AuthResponse;
import com.johnmartin.auth.dto.response.Result;
import com.johnmartin.auth.enums.VerificationStatus;
import com.johnmartin.auth.service.AuthService;
import com.johnmartin.auth.service.VerificationTokenService;
import com.johnmartin.auth.utils.ApiResponseUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiConstants.Path.API_AUTH)
public class AuthController {

    private final AuthService authService;
    private final VerificationTokenService verificationTokenService;

    public AuthController(AuthService authService, VerificationTokenService verificationTokenService) {
        this.authService = authService;
        this.verificationTokenService = verificationTokenService;
    }

    @PostMapping(ApiConstants.Path.REGISTER)
    public ResponseEntity<Result<AuthResponse>> register(HttpServletRequest request,
                                                         @Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse response = authService.register(request, registerRequest);
        return ResponseEntity.ok(Result.success(response));
    }

    @PostMapping(ApiConstants.Path.LOGIN)
    public ResponseEntity<Result<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(Result.success(response));
    }

    @GetMapping(ApiConstants.Path.VERIFY)
    public ResponseEntity<Result<String>> verify(@RequestParam @NotBlank(message = ApiMessages.TOKEN_IS_REQUIRED) String token) {
        VerificationStatus status = verificationTokenService.verifyToken(token);
        if (status == VerificationStatus.ALREADY_VERIFIED) {
            return ApiResponseUtils.createOkResponse(UserMessages.USER_IS_ALREADY_VERIFIED);
        }
        return ApiResponseUtils.createOkResponse(UserMessages.USER_IS_VERIFIED);
    }
}
