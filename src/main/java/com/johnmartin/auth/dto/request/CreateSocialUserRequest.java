package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.NotBlank;

public record CreateSocialUserRequest(@NotBlank(message = ApiErrorMessages.User.USER_ID_IS_MISSING_IN_REQUEST) String id,
                                      @NotBlank(message = ApiErrorMessages.User.FIRST_NAME_IS_REQUIRED) String firstName,
                                      @NotBlank(message = ApiErrorMessages.User.LAST_NAME_IS_REQUIRED) String lastName,
                                      @NotBlank(message = ApiErrorMessages.User.EMAIL_IS_REQUIRED) String email) {

    @Override
    public String toString() {
        return "CreateUserRequest{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='"
               + lastName + '\'' + ", email='" + email + '\'' + '}';
    }
}
