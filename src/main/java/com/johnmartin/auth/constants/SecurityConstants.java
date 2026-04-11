package com.johnmartin.auth.constants;

public final class SecurityConstants {

    private SecurityConstants() {
    }

    public static final int TOKEN_EXPIRY_HOURS = 24;

    public static class Attribute {
        public static final String REQUEST_ID = "requestId";
        public static final String AUTHORIZATION = "Authorization";
    }
}
