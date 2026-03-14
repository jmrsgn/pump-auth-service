package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = ApiErrorMessages.User.EMAIL_IS_REQUIRED) @Email(message = ApiErrorMessages.User.EMAIL_MUST_BE_VALID) String email,
                           @NotBlank(message = ApiErrorMessages.User.PASSWORD_IS_REQUIRED) String password) {
    @Override
    public String toString() {
        return "LoginRequest{" + "email='" + email + '\'' + ", password='[PROTECTED]'" + '}';
    }
}
