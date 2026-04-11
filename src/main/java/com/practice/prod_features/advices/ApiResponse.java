package com.practice.prod_features.advices;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class ApiResponse<T>{
    private T data;

    private ApiError apiError;

    private LocalDateTime timeStamp;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this();
        this.apiError = error;
    }
}
