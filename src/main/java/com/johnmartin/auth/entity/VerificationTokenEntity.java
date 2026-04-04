package com.johnmartin.auth.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.johnmartin.auth.constants.entities.VerificationTokenEntityConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = VerificationTokenEntityConstants.TABLE_NAME)
public class VerificationTokenEntity {
    @Id
    @Column(nullable = false, updatable = false, name = VerificationTokenEntityConstants.COLUMN_TOKEN)
    private String token;

    @Column(nullable = false, name = VerificationTokenEntityConstants.COLUMN_USER_ID)
    private UUID userId;

    @Column(nullable = false, name = VerificationTokenEntityConstants.COLUMN_EXPIRY_DATE)
    private LocalDateTime expiryDate;
}
