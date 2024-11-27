package com.beginwork.fashionserver.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandling {

    // Generic method to handle exceptions
    private <T extends Exception> ResponseEntity<ErrorResponse> handleException(T exception, String error, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setCode(status.value());
        errorResponse.setError(error);

        return new ResponseEntity<>(errorResponse, status); // Adjust status code based on error type
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return handleException(exception, "Resource not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ErrorResponse> handleResourceConflictException(ResourceConflictException exception) {
        return handleException(exception, "Resource conflict", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException (UnauthorizedException exception) {
        return handleException(exception, "Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JWTException.class)
    public ResponseEntity<ErrorResponse> handelJwtException (JWTException exception) {
        return handleException(exception, "Jwt error", HttpStatus.BAD_GATEWAY);
    }
}
