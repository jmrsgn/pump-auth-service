package com.johnmartin.auth.constants.entities;

public class UserEntityConstants {

    private UserEntityConstants() {
    }

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD_HASH = "password_hash";
    public static final String COLUMN_ENABLED = "enabled";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    public static class Roles {
        public static final String TABLE_NAME = "user_roles";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_ROLE_ID = "role_id";
    }
}
