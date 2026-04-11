package com.johnmartin.auth.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {

    ADMIN(1), CLIENT(2), COACH(3);

    private final int code;

    Role(int code) {
        this.code = code;
    }

    /**
     * Returns the numeric code of the role.
     * 
     * @return int value of the role
     */
    @JsonValue
    public int getCode() {
        return code;
    }

    /**
     * Converts a numeric code to its corresponding Role.
     *
     * @param code
     *            the role code
     * @return matching Role enum
     * @throws IllegalArgumentException
     *             if the code is invalid
     */
    @JsonCreator
    public static Role fromCode(int code) {
        for (Role role : values()) {
            if (role.code == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role code: " + code);
    }
}
