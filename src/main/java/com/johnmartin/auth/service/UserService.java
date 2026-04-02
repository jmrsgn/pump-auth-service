package com.johnmartin.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.johnmartin.auth.constants.api.ApiErrorMessages;
import com.johnmartin.auth.entities.UserEntity;
import com.johnmartin.auth.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public Optional<UserEntity> findById(String userId) {
        return userRepository.findById(userId);
    }

    public UserEntity createUser(UserEntity userEntity) {
        try {
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException(ApiErrorMessages.User.FAILED_TO_CREATE_USER);
        }
    }
}
