package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.annotations.Sensitive;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(@NotBlank(message = "First name is required") String firstName,
                              @NotBlank(message = "Last name is required") String lastName,
                              @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,
                              @Pattern(regexp = "^9\\d{9}$", message = "Invalid phone number") @Size(min = 10, max = 10) String phone,
                              int role,
                              @Sensitive @NotBlank(message = "Password is required") String password) {
}
