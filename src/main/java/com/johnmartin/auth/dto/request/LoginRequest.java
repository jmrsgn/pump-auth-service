package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.annotations.Sensitive;
import com.johnmartin.auth.constants.api.messages.UserMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank(message = UserMessages.EMAIL_IS_REQUIRED) @Email(message = UserMessages.EMAIL_MUST_BE_VALID) String email,
                           @Sensitive @NotBlank(message = UserMessages.PASSWORD_IS_REQUIRED) String password) {
}
