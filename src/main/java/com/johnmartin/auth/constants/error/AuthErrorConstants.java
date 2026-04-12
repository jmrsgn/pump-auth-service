package com.johnmartin.auth.constants.error;

public final class AuthErrorConstants {

    private AuthErrorConstants() {
    }

    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String TOKEN_IS_EXPIRED = "Token is expired";
    public static final String USER_IS_NOT_AUTHENTICATED_OR_INVALID_TOKEN = "User is not authenticated or invalid token";
}
