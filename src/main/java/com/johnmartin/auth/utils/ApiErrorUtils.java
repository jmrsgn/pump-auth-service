package com.johnmartin.auth.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.johnmartin.auth.constants.api.ApiConstants;
import com.johnmartin.auth.constants.api.ApiErrorMessages;
import com.johnmartin.auth.dto.response.ApiErrorResponse;
import com.johnmartin.auth.dto.response.Result;

public class ApiErrorUtils {

    public static <T> ResponseEntity<Result<T>> createInternalServerErrorResponse(String message) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                                  .withError(ApiConstants.Error.INTERNAL_SERVER_ERROR)
                                                                  .withMessage(message);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.failure(apiErrorResponse));
    }

    public static <T> ResponseEntity<Result<T>> createNotFoundErrorResponse(String message) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse().withStatus(HttpStatus.NOT_FOUND.value())
                                                                  .withError(ApiConstants.Error.NOT_FOUND)
                                                                  .withMessage(message);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Result.failure(apiErrorResponse));
    }

    public static <T> ResponseEntity<Result<T>> createUnauthorizedErrorResponse(String message) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse().withStatus(HttpStatus.UNAUTHORIZED.value())
                                                                  .withError(ApiConstants.Error.UNAUTHORIZED)
                                                                  .withMessage(StringUtils.defaultIfBlank(message,
                                                                                                          ApiErrorMessages.User.USER_IS_NOT_AUTHENTICATED));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.failure(apiErrorResponse));
    }

    public static <T> ResponseEntity<Result<T>> createBadRequestErrorResponse(String message) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse().withStatus(HttpStatus.BAD_REQUEST.value())
                                                                  .withError(ApiConstants.Error.BAD_REQUEST)
                                                                  .withMessage(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.failure(apiErrorResponse));
    }

    public static <T> ResponseEntity<Result<T>> createConflictErrorResponse(String message) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse().withStatus(HttpStatus.CONFLICT.value())
                                                                  .withError(ApiConstants.Error.CONFLICT)
                                                                  .withMessage(message);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Result.failure(apiErrorResponse));
    }
}
