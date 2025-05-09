package com.totfd.lms.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final LocalDateTime timestamp;
    private final boolean success;
    private final T data;
    private final String message;
    private final Integer status;
    private final String path; // New field for the request path
    private final Object errorDetails; // Optional: send stack trace, validation issues, etc.

    // Success factory method with status and path
    public static <T> ApiResponse<T> success(T data, String message, Integer status, String path) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .data(data)
                .message(message)
                .status(status)
                .path(path)
                .build();
    }

    // Error factory method
    public static <T> ApiResponse<T> error(String message, int status) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(false)
                .message(message)
                .status(status)
                .build();
    }

    // Error factory method with path and details
    public static <T> ApiResponse<T> error(String message, int status, String path, Object errorDetails) {
        return ApiResponse.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(false)
                .message(message)
                .status(status)
                .path(path)
                .errorDetails(errorDetails) // Set the error details (e.g., exception details)
                .build();
    }
}
