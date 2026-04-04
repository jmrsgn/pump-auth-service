package com.johnmartin.auth.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.johnmartin.auth.dto.response.ApiErrorResponse;
import com.johnmartin.auth.dto.response.Result;
import com.johnmartin.auth.utilities.LoggerUtility;
import com.johnmartin.auth.utils.ApiResponseUtils;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Class<GlobalExceptionHandler> clazz = GlobalExceptionHandler.class;

    /**
     * Global exception handlers
     *
     * errorMessage - message that is displayed for users
     * 
     * ex - thrown exception
     */

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleBadRequestException(String errorMessage,
                                                                              BadRequestException ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createBadRequestErrorResponse(errorMessage);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleUnauthorizedException(String errorMessage,
                                                                                UnauthorizedException ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createUnauthorizedErrorResponse(errorMessage);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleNotFoundException(String errorMessage, NotFoundException ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createNotFoundErrorResponse(errorMessage);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleConflictException(String errorMessage, ConflictException ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createConflictErrorResponse(errorMessage);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleForbiddenException(String errorMessage,
                                                                             ConflictException ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createForbiddenErrorResponse(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleException(String errorMessage, Exception ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createInternalServerErrorResponse(errorMessage);
    }

    /**
     * Handle method argument not valid exception (used to validate request objects)
     *
     * @param ex
     *            - MethodArgumentNotValidException
     * @return ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // Get the first error message will be thrown in Bean annotations for requests
        String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ApiResponseUtils.createBadRequestErrorResponse(message);
    }

    /**
     * Handle constraint violated exception (used for validating path variables)
     *
     * @param ex
     *            - ConstraintViolationException
     * @return ResponseEntity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        return ApiResponseUtils.createBadRequestErrorResponse(message);
    }

}
