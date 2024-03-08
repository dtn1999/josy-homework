package com.project.mediahub.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success;
    private Object data;

    public static ApiResponse success(String message, Object data) {
        return ApiResponse.builder()
                .message(message)
                .success(true)
                .data(data)
                .build();
    }

    public static ApiResponse failure(String message) {
        return ApiResponse.builder()
                .message(message)
                .success(false)
                .build();
    }

}
