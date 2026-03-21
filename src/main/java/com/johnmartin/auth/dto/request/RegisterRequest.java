package com.johnmartin.auth.dto.request;

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
                              @NotBlank(message = ApiErrorMessages.User.PASSWORD_IS_REQUIRED) String password) {

    @Override
    public String toString() {
        return "RegisterRequest{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='"
               + email + '\'' + ", phone='" + phone + '\'' + ", role=" + role + ", password='" + password + '\'' + '}';
    }
}
