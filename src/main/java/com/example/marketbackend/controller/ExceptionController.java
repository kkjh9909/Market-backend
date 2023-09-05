package com.example.marketbackend.controller;

import com.example.marketbackend.exception.ExceptionResponse;
import com.example.marketbackend.exception.LoginException;
import com.example.marketbackend.exception.response.LoginExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> handleLoginException(RuntimeException e) {
        return ResponseEntity.status(404).body(new LoginExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(404).body(new ExceptionResponse(e.getMessage(), 404));
    }
}
