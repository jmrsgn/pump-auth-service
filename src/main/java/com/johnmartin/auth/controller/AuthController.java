package com.johnmartin.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.dto.request.LoginRequest;
import com.johnmartin.auth.dto.request.RegisterRequest;
import com.johnmartin.auth.dto.response.AuthResponse;
import com.johnmartin.auth.dto.response.Result;
import com.johnmartin.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiConstants.Path.API_AUTH)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(ApiConstants.Path.REGISTER)
    public ResponseEntity<Result<AuthResponse>> register(HttpServletRequest request,
                                                         @Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse response = authService.register(request, registerRequest);
        return ResponseEntity.ok(Result.success(response));
    }

    @PostMapping(ApiConstants.Path.LOGIN)
    public ResponseEntity<Result<AuthResponse>> login(HttpServletRequest request,
                                                      @Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(request, loginRequest);
        return ResponseEntity.ok(Result.success(response));
    }
}
