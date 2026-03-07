package com.johnmartin.auth.dto.request;

import com.johnmartin.auth.constants.api.ApiErrorMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    private String firstName;
    private String lastName;

    @NotBlank(message = ApiErrorMessages.EMAIL_IS_REQUIRED)
    @Email(message = ApiErrorMessages.EMAIL_MUST_BE_VALID)
    private String email;
    private String phone;

    @NotBlank(message = ApiErrorMessages.PASSWORD_IS_REQUIRED)
    private String password;

    @Override
    public String toString() {
        return "RegisterRequest{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='"
               + email + '\'' + ", phone='" + phone + '\'' + ", password='" + password + '\'' + '}';
    }
}
