package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    @NotBlank(message = ApiErrorMessages.EMAIL_IS_REQUIRED)
    @Email(message = ApiErrorMessages.EMAIL_MUST_BE_VALID)
    private String email;

    @NotBlank(message = ApiErrorMessages.PASSWORD_IS_REQUIRED)
    private String password;

    @Override
    public String toString() {
        return "LoginRequest{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
    }
}
