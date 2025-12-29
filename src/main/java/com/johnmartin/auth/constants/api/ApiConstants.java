package com.johnmartin.auth.constants.api;

public class ApiConstants {

    public static final String APP_NAME = "Pump API";

    public static class Path {
        public static final String API_BASE_V1 = "/api/v1";
        public static final String API_BASE_V1_INTERNAL = "/api/v1/internal";
        public static final String HEALTH = "/actuator/health";
        public static final String VALIDATE = "/validate";

        // Auth
        public static final String API_AUTH = API_BASE_V1 + "/auth";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";

        public static final String API_AUTH_INTERNAL = API_BASE_V1_INTERNAL + "/auth";
    }

    public static class Error {
        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String NOT_FOUND = "Not found";
        public static final String BAD_REQUEST = "Bad Request";
        public static final String CONFLICT = "Conflict";
        public static final String FORBIDDEN = "Forbidden";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    }
}
