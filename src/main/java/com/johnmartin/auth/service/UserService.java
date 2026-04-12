package com.johnmartin.auth.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.johnmartin.auth.constants.error.ExternalServiceErrorConstants;
import com.johnmartin.auth.constants.error.UserErrorConstants;
import com.johnmartin.auth.entity.UserEntity;
import com.johnmartin.auth.exception.NotFoundException;
import com.johnmartin.auth.repository.UserRepository;
import com.johnmartin.auth.utilities.LoggerUtility;

@Service
public class UserService {

    private static final Class<UserService> clazz = UserService.class;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity findById(UUID userId) {
        LoggerUtility.d(clazz, "Execute method: [findById]");
        return userRepository.findById(userId)
                             .orElseThrow(() -> new NotFoundException(UserErrorConstants.USER_NOT_FOUND));
    }

    public UserEntity findByEmail(String email) {
        LoggerUtility.d(clazz, "Execute method: [findById]");
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new NotFoundException(UserErrorConstants.USER_NOT_FOUND));
    }

    public Optional<UserEntity> findOptionalByEmail(String email) {
        LoggerUtility.d(clazz, "Execute method: [findOptionalByEmail]");
        return userRepository.findByEmail(email);
    }

    public void deleteById(UUID userId) {
        LoggerUtility.d(clazz, "Execute method: [deleteById]");
        userRepository.deleteById(userId);
    }

    public UserEntity createUser(UserEntity userEntity) {
        try {
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException(ExternalServiceErrorConstants.FAILED_TO_CREATE_USER);
        }
    }
}
