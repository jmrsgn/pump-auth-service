package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.annotations.Sensitive;
import com.johnmartin.auth.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = ApiErrorMessages.User.EMAIL_IS_REQUIRED) @Email(message = ApiErrorMessages.User.EMAIL_MUST_BE_VALID) String email,
                           @Sensitive @NotBlank(message = ApiErrorMessages.User.PASSWORD_IS_REQUIRED) String password) {
}
