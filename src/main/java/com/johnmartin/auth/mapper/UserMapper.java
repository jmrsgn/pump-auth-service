package com.johnmartin.auth.mapper;

import com.johnmartin.auth.dto.response.UserResponse;
import com.johnmartin.auth.entities.UserEntity;

public class UserMapper {

    public static UserResponse toResponse(UserEntity userEntity) {
        return new UserResponse().withId(userEntity.getId().toString())
                                 .withFirstName(userEntity.getFirstName())
                                 .withLastName(userEntity.getLastName())
                                 .withEmail(userEntity.getEmail())
                                 .withPhone(userEntity.getPhone())
                                 .withProfileImage(userEntity.getProfileImage());
    }
}
