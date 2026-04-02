package com.practice.prod_features.advices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiResponse<T>{
    private T data;

    private ApiError apiError;

    private LocalDateTime timeStamp;

    public ApiResponse(T data) {
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data, LocalDateTime timeStamp) {
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public ApiResponse(ApiError apiError) {
        this.apiError = apiError;
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(ApiError apiError, LocalDateTime timeStamp) {
        this.apiError = apiError;
        this.timeStamp = timeStamp;
    }
}
