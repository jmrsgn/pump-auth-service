package com.johnmartin.auth.constants.entities;

public final class VerificationTokenEntityConstants {

    private VerificationTokenEntityConstants() {
    }

    public static final String TABLE_NAME = "verification_tokens";
    public static final String COLUMN_TOKEN = "token";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_EXPIRY_DATE = "expiry_date";
}
