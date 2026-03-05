package com.biblioteca.cqrs.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalStateException(IllegalStateException ex, HttpServletRequest request) {
        HttpStatus status;
        String error;
        String message = ex.getMessage();

        if (message != null && message.contains("No existe un libro")) {
            status = HttpStatus.NOT_FOUND;
            error = status.getReasonPhrase();
        } else if (message != null && message.contains("No se puede prestar")) {
            status = HttpStatus.CONFLICT;
            error = status.getReasonPhrase();
        } else {
            status = HttpStatus.BAD_REQUEST;
            error = status.getReasonPhrase();
        }

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message != null ? message : "Error interno del sistema");
        body.put("path", request.getRequestURI());

        return ResponseEntity.status(status).body(body);
    }
}
