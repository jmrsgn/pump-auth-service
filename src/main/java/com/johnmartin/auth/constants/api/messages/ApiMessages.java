package com.johnmartin.auth.constants.api.messages;

public class ApiMessages {

    private ApiMessages() {
    }

    // Error messages
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String USER_ID_CANNOT_BE_BLANK_WHEN_GENERATING_TOKEN = "User ID cannot be blank when generating token";
    public static final String JWT_SECRET_CANNOT_BE_NULL_OR_EMPTY = "JWT secret cannot be null or empty";
    public static final String TOKEN_IS_REQUIRED = "Token is required";
    public static final String TOKEN_IS_EXPIRED = "Token is expired";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String INVALID_EVENT_DATA = "Invalid event data";
    public static final String EMAIL_IS_REQUIRED = "Email is required";
    public static final String VERIFICATION_LINK_IS_REQUIRED = "Verification link is required";
    public static final String FAILED_TO_SEND_EMAIL = "Failed to send email";
}
