package com.johnmartin.auth.mapper;

import com.johnmartin.auth.dto.response.UserResponse;
import com.johnmartin.auth.entities.UserEntity;

public class UserMapper {

    public static UserResponse toResponse(UserEntity userEntity) {
        return new UserResponse(userEntity.getId(),
                                userEntity.getFirstName(),
                                userEntity.getLastName(),
                                userEntity.getEmail(),
                                userEntity.getPhone(),
                                userEntity.getRole(),
                                userEntity.getProfileImageUrl());
    }
}
