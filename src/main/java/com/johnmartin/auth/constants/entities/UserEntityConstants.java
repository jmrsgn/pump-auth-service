package com.johnmartin.auth.constants.entities;

public class UserEntityConstants {

    private UserEntityConstants() {
    }

    public static final String TABLE_NAME = "USERS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PHONE = "PHONE";
    public static final String COLUMN_PASSWORD_HASH = "PASSWORD_HASH";
    public static final String COLUMN_ENABLED = "ENABLED";
    public static final String COLUMN_CREATED_AT = "CREATED_AT";
    public static final String COLUMN_UPDATED_AT = "UPDATED_AT";

    public static class Roles {
        public static final String TABLE_NAME = "USER_ROLES";
        public static final String COLUMN_USER_ID = "USER_ID";
        public static final String COLUMN_ROLE_ID = "ROLE_ID";
    }
}
