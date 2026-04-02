package com.practice.prod_features.advices;

import com.practice.prod_features.exceptions.ResourceNotFoundException;
import jakarta.persistence.ExcludeDefaultListeners;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex){
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        ApiResponse<?> apiResponse = covertApiError(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    public ApiResponse<?> covertApiError(ApiError apiError){
        return new ApiResponse<>(apiError);
    }
}
