package com.johnmartin.auth.constants.api.messages;

public class UserMessages {

    private UserMessages() {
    }

    public static final String USER_IS_ALREADY_VERIFIED = "User is already verified";
    public static final String USER_IS_VERIFIED = "User is verified";

    // Error messages
    public static final String INVALID_CREDENTIALS = "Invalid Credentials";
    public static final String EMAIL_AND_PASSWORD_ARE_REQUIRED = "Email and password are required";
    public static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS = "User with this email already exists";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String EMAIL_IS_REQUIRED = "Email is required";
    public static final String PASSWORD_IS_REQUIRED = "Password is required";
    public static final String EMAIL_MUST_BE_VALID = "Email must be valid";
    public static final String FAILED_TO_CREATE_USER = "Failed to create user";
    public static final String FAILED_TO_CREATE_SOCIAL_USER = "Failed to create social user";
    public static final String USER_ID_IS_REQUIRED = "User ID is required";
    public static final String USER_IS_NOT_AUTHENTICATED_OR_INVALID_TOKEN = "User is not authenticated or invalid token";

    public static final String USER_ID_IS_MISSING_IN_REQUEST = "User ID is missing in request";
    public static final String FIRST_NAME_IS_REQUIRED = "First name is required";
    public static final String LAST_NAME_IS_REQUIRED = "Last name is required";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number";
}
