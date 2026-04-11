package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.constants.api.ApiErrorConstants;

import jakarta.validation.constraints.NotBlank;

public record CreateSocialUserRequest(@NotBlank(message = ApiErrorConstants.USER_ID_IS_MISSING_IN_REQUEST) String userId,
                                      @NotBlank(message = ApiErrorConstants.FIRST_NAME_IS_REQUIRED) String firstName,
                                      @NotBlank(message = ApiErrorConstants.LAST_NAME_IS_REQUIRED) String lastName,
                                      @NotBlank(message = ApiErrorConstants.EMAIL_IS_REQUIRED) String email) {
}
