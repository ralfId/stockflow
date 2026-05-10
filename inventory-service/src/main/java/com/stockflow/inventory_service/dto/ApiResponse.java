package com.stockflow.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private int statusCode;
    private T data;

    public static <T> ApiResponse<T> success(T data, int statusCode) {
        return ApiResponse.<T>builder()
                .success(true)
                .statusCode(statusCode)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(int statusCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .statusCode(statusCode)
                .data(null)
                .build();
    }
}
