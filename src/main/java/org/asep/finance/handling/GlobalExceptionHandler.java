package org.asep.finance.handling;

import org.asep.finance.handling.exception.ExternalApiException;
import org.asep.finance.handling.exception.ResourceTypeNotFoundException;
import org.asep.finance.handling.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceTypeNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            ResourceTypeNotFoundException ex) {

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleExternal(
            ExternalApiException ex) {

        return ResponseEntity.status(503)
                .body(ApiResponse.error(503, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric() {
        return ResponseEntity.status(500)
                .body(ApiResponse.error(500, "Internal server error"));
    }
}
