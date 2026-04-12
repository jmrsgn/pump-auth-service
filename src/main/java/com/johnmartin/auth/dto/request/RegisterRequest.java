package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.annotations.Sensitive;
import com.johnmartin.auth.constants.ValidationConstants;
import com.johnmartin.auth.constants.error.ValidationErrorConstants;
import com.johnmartin.auth.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank(message = ValidationErrorConstants.FIRST_NAME_IS_REQUIRED) String firstName,
                              @NotBlank(message = ValidationErrorConstants.LAST_NAME_IS_REQUIRED) String lastName,
                              @NotBlank(message = ValidationErrorConstants.EMAIL_IS_REQUIRED) @Email(message = ValidationErrorConstants.ENTER_A_VALID_EMAIL) String email,
                              @Pattern(regexp = ValidationConstants.REGEX_MOBILE_NUMBER, message = ValidationErrorConstants.ENTER_A_VALID_PHONE_NUMBER) @Size(min = ValidationConstants.MAX_MOBILE_NUMBER_LENGTH, max = ValidationConstants.MAX_MOBILE_NUMBER_LENGTH) String phone,
                              Role role,
                              @Sensitive @NotBlank(message = ValidationErrorConstants.PASSWORD_IS_REQUIRED) String password) {

    public RegisterRequest {
        // Default role to client if not provided in request
        role = (role == null) ? Role.CLIENT : role;
    }
}
