package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.constants.error.ValidationErrorConstants;

import jakarta.validation.constraints.NotBlank;

public record CreateSocialUserRequest(@NotBlank(message = ValidationErrorConstants.USER_ID_IS_REQUIRED) String userId,
                                      @NotBlank(message = ValidationErrorConstants.FIRST_NAME_IS_REQUIRED) String firstName,
                                      @NotBlank(message = ValidationErrorConstants.LAST_NAME_IS_REQUIRED) String lastName,
                                      @NotBlank(message = ValidationErrorConstants.EMAIL_IS_REQUIRED) String email) {
}
