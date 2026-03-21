package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.annotations.Sensitive;
import com.johnmartin.auth.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(String firstName,
                              String lastName,
                              @NotBlank(message = ApiErrorMessages.User.EMAIL_IS_REQUIRED) @Email(message = ApiErrorMessages.User.EMAIL_MUST_BE_VALID) String email,
                              @Pattern(regexp = "^9\\d{9}$", message = ApiErrorMessages.User.INVALID_PHONE_NUMBER) @Size(min = 10, max = 10) String phone,
                              int role,
                              @Sensitive @NotBlank(message = ApiErrorMessages.User.PASSWORD_IS_REQUIRED) String password) {
}
