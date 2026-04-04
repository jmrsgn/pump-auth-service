package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.annotations.Sensitive;
import com.johnmartin.auth.constants.api.messages.UserMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(String firstName,
                              String lastName,
                              @NotBlank(message = UserMessages.EMAIL_IS_REQUIRED) @Email(message = UserMessages.EMAIL_MUST_BE_VALID) String email,
                              @Pattern(regexp = "^9\\d{9}$", message = UserMessages.INVALID_PHONE_NUMBER) @Size(min = 10, max = 10) String phone,
                              int role,
                              @Sensitive @NotBlank(message = UserMessages.PASSWORD_IS_REQUIRED) String password) {
}
