package com.johnmartin.auth.entities;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.johnmartin.auth.constants.entities.UserEntityConstants;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = UserEntityConstants.TABLE_NAME)
public class UserEntity {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String passwordHash;
    private String profileImage;
    private Boolean enabled;
    private Date createdAt;
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName)
               && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email)
               && Objects.equals(phone, that.phone) && Objects.equals(passwordHash, that.passwordHash)
               && Objects.equals(profileImage, that.profileImage) && Objects.equals(enabled, that.enabled)
               && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                            firstName,
                            lastName,
                            email,
                            phone,
                            passwordHash,
                            profileImage,
                            enabled,
                            createdAt,
                            updatedAt);
    }

    @Override
    public String toString() {
        return "UserEntity{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
               + '\'' + ", email='" + email + '\'' + ", phone='" + phone + '\'' + ", passwordHash='" + passwordHash
               + '\'' + ", profileImage='" + profileImage + '\'' + ", enabled=" + enabled + ", createdAt=" + createdAt
               + ", updatedAt=" + updatedAt + '}';
    }
}
