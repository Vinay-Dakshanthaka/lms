package com.totfd.lms.exceptions;

import com.totfd.lms.payload.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Generic fallback exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex, HttpServletRequest request) {
//        ex.printStackTrace();
        return new ResponseEntity<>(
                ApiResponse.error(
                        "Internal server error: " + ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        request.getRequestURI(),
                        null
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        request.getRequestURI(),
                        null
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        request.getRequestURI(),
                        null
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(
                        "Invalid email or password.",
                        HttpStatus.UNAUTHORIZED.value(),
                        request.getRequestURI(),
                        null
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(
                        "Authentication failed: " + ex.getMessage(),
                        HttpStatus.UNAUTHORIZED.value(),
                        request.getRequestURI(),
                        null
                ),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(
                        "Access denied: insufficient permissions",
                        HttpStatus.FORBIDDEN.value(),
                        request.getRequestURI(),
                        null
                ),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleJsonParseError(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(
                        "Invalid input format",
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI(),
                        ex.getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<?>> duplicateEmail(DuplicateEmailException ex, HttpServletRequest request) {
        return new ResponseEntity<>(
                ApiResponse.error(
                        "Duplicate Email",
                        HttpStatus.CONFLICT.value(),
                        request.getRequestURI(),
                        ex.getMessage()
                ),
                HttpStatus.CONFLICT
        );
    }
}
