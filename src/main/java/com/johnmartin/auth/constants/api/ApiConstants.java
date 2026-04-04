package com.johnmartin.auth.constants.api;

public class ApiConstants {

    private ApiConstants() {
    }

    public static final String API_BASE_V1 = "/api/v1";
    public static final String API_BASE_V1_INTERNAL = "/api/v1/internal";

    public static class Path {
        public static final String ACTUATOR = "/actuator";
        public static final String HEALTH = "/health";

        // Auth
        public static final String API_AUTH = API_BASE_V1 + "/auth";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";
        public static final String VERIFY = "/verify";
    }

    public static class InternalPath {
        public static final String API_AUTH_INTERNAL = API_BASE_V1_INTERNAL + "/auth";
        public static final String VALIDATE = "/validate";
    }

    public static class Error {
        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String NOT_FOUND = "Not found";
        public static final String BAD_REQUEST = "Bad Request";
        public static final String CONFLICT = "Conflict";
        public static final String FORBIDDEN = "Forbidden";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    }

    public static class PumpSocialService {
        // public static final String URL = "http://pump-auth-service:8080";
        public static final String URL = "http://localhost:8081";
        public static final String API_CREATE_USER = API_BASE_V1_INTERNAL + "/user/create";
    }
}
