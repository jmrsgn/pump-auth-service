package com.johnmartin.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateSocialUserRequest(@NotBlank(message = "User ID is missing in request") String id,
                                      @NotBlank(message = "First name is required") String firstName,
                                      @NotBlank(message = "Last name is required") String lastName,
                                      @NotBlank(message = "Email is required") String email) {
}
