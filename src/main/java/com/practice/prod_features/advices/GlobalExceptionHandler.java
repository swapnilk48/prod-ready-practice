package com.practice.prod_features.advices;

import com.practice.prod_features.exceptions.ResourceNotFoundException;
import com.practice.prod_features.exceptions.UserAlreadyExists;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.ExcludeDefaultListeners;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.JMException;

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
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.CONFLICT);
        ApiResponse<?> apiResponse = covertApiError(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException ex){
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        ApiResponse<?> apiResponse = covertApiError(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException ex){
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        ApiResponse<?> apiResponse = covertApiError(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex){
        ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        ApiResponse<?> apiResponse = covertApiError(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    public ApiResponse<?> covertApiError(ApiError apiError){
        return new ApiResponse<>(apiError);
    }
}
