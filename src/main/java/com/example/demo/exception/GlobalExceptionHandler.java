package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerNotFoundException.class)
    public ResponseEntity<String> handleServerNotFound(ServerNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404 error
                .body(e.getMessage());
    }
}
