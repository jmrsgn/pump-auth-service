package com.johnmartin.auth.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.dto.response.ApiErrorResponse;
import com.johnmartin.auth.dto.response.Result;

public class ApiErrorUtils {

    public static <T> ResponseEntity<Result<T>> createInternalServerErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(Result.failure(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                                       ApiConstants.Error.INTERNAL_SERVER_ERROR,
                                                                       message)));
    }

    public static <T> ResponseEntity<Result<T>> createNotFoundErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(Result.failure(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(),
                                                                       ApiConstants.Error.NOT_FOUND,
                                                                       message)));
    }

    public static <T> ResponseEntity<Result<T>> createUnauthorizedErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(Result.failure(new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                                                                       ApiConstants.Error.UNAUTHORIZED,
                                                                       message)));
    }

    public static <T> ResponseEntity<Result<T>> createBadRequestErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(Result.failure(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                                                                       ApiConstants.Error.BAD_REQUEST,
                                                                       message)));
    }

    public static <T> ResponseEntity<Result<T>> createConflictErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(Result.failure(new ApiErrorResponse(HttpStatus.CONFLICT.value(),
                                                                       ApiConstants.Error.CONFLICT,
                                                                       message)));
    }

    public static <T> ResponseEntity<Result<T>> createForbiddenErrorResponse(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(Result.failure(new ApiErrorResponse(HttpStatus.FORBIDDEN.value(),
                                                                       ApiConstants.Error.FORBIDDEN,
                                                                       message)));
    }
}
