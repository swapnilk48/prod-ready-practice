package com.practice.prod_features.advices;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {

    private LocalDateTime timeStamp;

    private String error;

    private HttpStatus httpStatus;

    public ApiError() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError(String error, HttpStatus httpStatus){
        this();
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
