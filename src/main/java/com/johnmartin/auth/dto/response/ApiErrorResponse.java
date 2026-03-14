package com.johnmartin.auth.dto.response;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorResponse {

    private int status;
    private String error;
    private String message;
    private Instant timestamp;

    public ApiErrorResponse() {
        this.timestamp = Instant.now();
    }

    public ApiErrorResponse withStatus(int status) {
        setStatus(status);
        return this;
    }

    public ApiErrorResponse withError(String error) {
        setError(error);
        return this;
    }

    public ApiErrorResponse withMessage(String message) {
        setMessage(message);
        return this;
    }
}
