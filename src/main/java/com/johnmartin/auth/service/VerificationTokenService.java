package com.johnmartin.auth.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.johnmartin.auth.entity.UserEntity;
import com.johnmartin.auth.entity.VerificationTokenEntity;
import com.johnmartin.auth.enums.VerificationStatus;
import com.johnmartin.auth.exception.BadRequestException;
import com.johnmartin.auth.exception.NotFoundException;
import com.johnmartin.auth.repository.VerificationTokenRepository;
import com.johnmartin.auth.utilities.LoggerUtility;

@Service
public class VerificationTokenService {

    private static final Class<VerificationTokenService> clazz = VerificationTokenService.class;

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserService userService;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository, UserService userService) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userService = userService;
    }

    public String generateToken(UUID userId) {
        LoggerUtility.d(clazz, "Execute method: [generateToken]");
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        // Delete old tokens (cleanup)
        verificationTokenRepository.deleteByUserId(userId);

        String token = UUID.randomUUID().toString();

        VerificationTokenEntity entity = new VerificationTokenEntity();
        entity.setToken(token);
        entity.setUserId(userId);
        entity.setExpiryDate(LocalDateTime.now().plusHours(24));
        verificationTokenRepository.save(entity);
        return token;
    }

    public VerificationStatus verifyToken(String token) {
        LoggerUtility.d(clazz, "Execute method: [verifyToken]");

        VerificationTokenEntity verificationToken = verificationTokenRepository.findByToken(token)
                                                                               .orElseThrow(() -> new NotFoundException("Invalid token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token is expired");
        }

        UserEntity user = userService.findById(verificationToken.getUserId());

        if (Boolean.TRUE.equals(user.getEnabled())) {
            return VerificationStatus.ALREADY_VERIFIED;
        }

        user.setEnabled(true);
        userService.save(user);
        return VerificationStatus.VERIFIED;
    }
}
