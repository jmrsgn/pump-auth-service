package com.johnmartin.auth.constants.api;

public final class ExternalServiceConstants {

    public static final String API_BASE_V1_INTERNAL = "/api/v1/internal";

    private ExternalServiceConstants() {
    }

    public static final class PumpSocialService {

        private PumpSocialService() {
        }

        public static final String API_USER_INTERNAL = API_BASE_V1_INTERNAL + "/users";
        public static final String API_CREATE_USER = API_USER_INTERNAL + "/create";
    }
}
