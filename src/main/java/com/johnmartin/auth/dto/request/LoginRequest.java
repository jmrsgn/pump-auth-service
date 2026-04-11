package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.annotations.Sensitive;
import com.johnmartin.auth.constants.api.ApiErrorConstants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = ApiErrorConstants.EMAIL_IS_REQUIRED) @Email(message = ApiErrorConstants.ENTER_A_VALID_EMAIL) String email,
                           @Sensitive @NotBlank(message = ApiErrorConstants.PASSWORD_IS_REQUIRED) String password) {
}
