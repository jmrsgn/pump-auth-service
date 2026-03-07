package com.johnmartin.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String profileImage;

    public UserResponse withId(String id) {
        setId(id);
        return this;
    }

    public UserResponse withFirstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public UserResponse withLastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public UserResponse withEmail(String email) {
        setEmail(email);
        return this;
    }

    public UserResponse withPhone(String phone) {
        setPhone(phone);
        return this;
    }

    public UserResponse withProfileImage(String profileImage) {
        setProfileImage(profileImage);
        return this;
    }
}
