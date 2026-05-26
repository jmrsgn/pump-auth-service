package com.johnmartin.auth.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.johnmartin.auth.constants.domain.UserConstants;
import com.johnmartin.auth.constants.error.SystemErrorConstants;

import lombok.Getter;

@Getter
public enum UserRole {

    ROLE_ADMIN(UserConstants.ADMIN), ROLE_USER(UserConstants.USER);

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    /**
     * Converts a numeric code to its corresponding Role.
     *
     * @param value
     *            the role value
     * @return matching Role enum
     * @throws IllegalArgumentException
     *             if the value is invalid
     */
    @JsonCreator
    public static UserRole fromCode(String value) {
        for (UserRole role : values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException(SystemErrorConstants.INVALID_ROLE_CODE + ": " + value);
    }
}
