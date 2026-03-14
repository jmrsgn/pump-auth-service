package com.johnmartin.auth.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.johnmartin.auth.constants.api.ApiErrorMessages;
import com.johnmartin.auth.entities.UserEntity;
import com.johnmartin.auth.repository.RoleRepository;
import com.johnmartin.auth.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public Optional<UserEntity> findById(String id) {
        return userRepository.findById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity createUser(UserEntity userEntity) {
        try {
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new RuntimeException(ApiErrorMessages.User.FAILED_TO_CREATE_USER);
        }
    }

    public Optional<UserEntity> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        // Registered as subject for Auth, is email
        return userRepository.findByEmail(auth.getName());
    }
}
