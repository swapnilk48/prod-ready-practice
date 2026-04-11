package com.practice.prod_features.advices;

import com.practice.prod_features.exceptions.ResourceNotFoundException;
import com.practice.prod_features.exceptions.UserAlreadyExists;
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

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ApiResponse<?>> handleUserAlreadyFound(UserAlreadyExists ex){
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        ApiResponse<?> apiResponse = covertApiError(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex){
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        ApiResponse<?> apiResponse = covertApiError(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    public ApiResponse<?> covertApiError(ApiError apiError){
        return new ApiResponse<>(apiError);
    }
}
