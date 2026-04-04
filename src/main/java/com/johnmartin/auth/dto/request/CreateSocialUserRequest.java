package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.constants.api.messages.UserMessages;

import jakarta.validation.constraints.NotBlank;

public record CreateSocialUserRequest(@NotBlank(message = UserMessages.USER_ID_IS_MISSING_IN_REQUEST) String id,
                                      @NotBlank(message = UserMessages.FIRST_NAME_IS_REQUIRED) String firstName,
                                      @NotBlank(message = UserMessages.LAST_NAME_IS_REQUIRED) String lastName,
                                      @NotBlank(message = UserMessages.EMAIL_IS_REQUIRED) String email) {
}
