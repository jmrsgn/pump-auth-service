package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.annotations.Sensitive;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,
                           @Sensitive @NotBlank(message = "Password is required") String password) {
}
