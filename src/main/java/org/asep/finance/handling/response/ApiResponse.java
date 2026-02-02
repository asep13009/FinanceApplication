package org.asep.finance.handling.response;

public record ApiResponse<T>(
        String status,
        int code,
        String message,
        T data
){
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                "SUCCESS",
                200,
                "Request processed successfully",
                data
        );
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(
                "ERROR",
                code,
                message,
                null
        );
    }
}