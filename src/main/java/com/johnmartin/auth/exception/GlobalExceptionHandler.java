package com.johnmartin.auth.exception;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
     * ex.getMessage() - message that is displayed for users
     *
     * ex - thrown exception
     */

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleBadRequestException(BadRequestException ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createBadRequestErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleUnauthorizedException(UnauthorizedException ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createUnauthorizedErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleNotFoundException(NotFoundException ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createNotFoundErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleConflictException(ConflictException ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createConflictErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleForbiddenException(ForbiddenException ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createForbiddenErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleException(Exception ex) {
        LoggerUtility.e(clazz, ex.getMessage(), ex);
        return ApiResponseUtils.createInternalServerErrorResponse(ex.getMessage());
    }

    /**
     * Handle method argument not valid exception (used to validate request objects)
     *
     * @param ex
     *            - MethodArgumentNotValidException
     * @return ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<ApiErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
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
    public ResponseEntity<Result<ApiErrorResponse>> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        return ApiResponseUtils.createBadRequestErrorResponse(message);
    }

}
