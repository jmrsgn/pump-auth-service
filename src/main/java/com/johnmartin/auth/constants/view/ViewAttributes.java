package com.johnmartin.auth.constants.view;

public class ViewAttributes {

    private ViewAttributes() {
    }

    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String VERIFICATION_LINK = "verificationLink";

    public static class Template {
        public static final String VERIFICATION_EMAIL = "verification-email";
        public static final String VERIFICATION_RESULT = "verification-result";
        public static final String VERIFICATION_ERROR = "verification-error";
    }
}
