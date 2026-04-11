package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.annotations.Sensitive;
import com.johnmartin.auth.constants.ValidationConstants;
import com.johnmartin.auth.constants.api.ApiErrorConstants;
import com.johnmartin.auth.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank(message = ApiErrorConstants.FIRST_NAME_IS_REQUIRED) String firstName,
                              @NotBlank(message = ApiErrorConstants.LAST_NAME_IS_REQUIRED) String lastName,
                              @NotBlank(message = ApiErrorConstants.EMAIL_IS_REQUIRED) @Email(message = ApiErrorConstants.ENTER_A_VALID_EMAIL) String email,
                              @Pattern(regexp = ValidationConstants.REGEX_MOBILE_NUMBER, message = ApiErrorConstants.ENTER_A_VALID_PHONE_NUMBER) @Size(min = 10, max = 10) String phone,
                              Role role,
                              @Sensitive @NotBlank(message = ApiErrorConstants.PASSWORD_IS_REQUIRED) String password) {

    public RegisterRequest {
        // Default role to client if not provided in request
        role = (role == null) ? Role.CLIENT : role;
    }
}
