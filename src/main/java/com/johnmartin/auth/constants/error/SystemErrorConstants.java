package com.johnmartin.auth.constants.error;

public final class SystemErrorConstants {

    private SystemErrorConstants() {
    }

    public static final String INVALID_EVENT_DATA = "Invalid event data";
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String JWT_SECRET_CANNOT_BE_NULL_OR_EMPTY = "JWT secret cannot be null or empty";
    public static final String USER_ID_CANNOT_BE_BLANK_WHEN_GENERATING_TOKEN = "User ID cannot be blank when generating token";
    public static final String INVALID_ROLE_CODE = "Invalid role code";
}
