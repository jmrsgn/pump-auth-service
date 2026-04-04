package com.johnmartin.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.johnmartin.auth.entity.VerificationTokenEntity;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, String> {

    Optional<VerificationTokenEntity> findByToken(String token);

    void deleteByUserId(UUID userId);
}
