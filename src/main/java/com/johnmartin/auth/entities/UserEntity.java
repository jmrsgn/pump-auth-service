package com.johnmartin.auth.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.johnmartin.auth.constants.entities.UserEntityConstants;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = UserEntityConstants.TABLE_NAME)
public class UserEntity {
    @Id
    @Column(name = UserEntityConstants.COLUMN_ID)
    private UUID id;

    @Column(name = UserEntityConstants.COLUMN_FIRST_NAME)
    private String firstName;

    @Column(name = UserEntityConstants.COLUMN_LAST_NAME)
    private String lastName;

    @Column(name = UserEntityConstants.COLUMN_EMAIL)
    private String email;

    @Column(name = UserEntityConstants.COLUMN_PHONE)
    private String phone;

    @Column(name = UserEntityConstants.COLUMN_PASSWORD_HASH)
    private String passwordHash;

    @Column(name = UserEntityConstants.COLUMN_ENABLED)
    private Boolean enabled;

    @CreationTimestamp
    @Column(name = UserEntityConstants.COLUMN_CREATED_AT, updatable = false)
    private Instant createdAt;

    @CreationTimestamp
    @Column(name = UserEntityConstants.COLUMN_UPDATED_AT)
    private Instant updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = UserEntityConstants.Roles.TABLE_NAME, joinColumns = @JoinColumn(name = UserEntityConstants.Roles.COLUMN_USER_ID), inverseJoinColumns = @JoinColumn(name = UserEntityConstants.Roles.COLUMN_ROLE_ID))
    private Set<RoleEntity> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName)
               && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email)
               && Objects.equals(phone, that.phone) && Objects.equals(passwordHash, that.passwordHash)
               && Objects.equals(enabled, that.enabled) && Objects.equals(createdAt, that.createdAt)
               && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phone, passwordHash, enabled, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
               + '\'' + ", email='" + email + '\'' + ", phone='" + phone + '\'' + ", passwordHash='" + passwordHash
               + '\'' + '\'' + ", enabled=" + enabled + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
