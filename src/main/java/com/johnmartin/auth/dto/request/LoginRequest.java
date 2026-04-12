package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.annotations.Sensitive;
import com.johnmartin.auth.constants.error.ValidationErrorConstants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = ValidationErrorConstants.EMAIL_IS_REQUIRED) @Email(message = ValidationErrorConstants.ENTER_A_VALID_EMAIL) String email,
                           @Sensitive @NotBlank(message = ValidationErrorConstants.PASSWORD_IS_REQUIRED) String password) {
}
