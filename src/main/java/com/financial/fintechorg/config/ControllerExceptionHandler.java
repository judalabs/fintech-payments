package com.financial.fintechorg.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.financial.fintechorg.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class, ResourceNotFoundException.class})
    public ResponseEntity<String> handleDuplicateResponse(Exception e) {
        log.error("hidden error from controllerAdvice: {}", e.getCause());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> threatGeneralException(Exception exception) {
        log.error("controllerAdvice: internal error caught: {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(exception.getCause().toString());
    }
}
