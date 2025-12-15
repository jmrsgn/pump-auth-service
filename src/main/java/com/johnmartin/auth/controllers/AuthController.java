package com.johnmartin.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiConstants.Path.API_AUTH)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(ApiConstants.Path.REGISTER)
    public ResponseEntity<Result<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(Result.success(response));
    }

    @PostMapping(ApiConstants.Path.LOGIN)
    public ResponseEntity<Result<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(Result.success(response));
    }
}
