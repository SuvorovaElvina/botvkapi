package com.task.botvk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CallbackExceptionHandler extends ResponseEntityExceptionHandler {
    private final String DEFAULT_RESPONSE = "ok";

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleOkCallback(RuntimeException ex, WebRequest request) {
        log.error("Возникла ошибка: {} по запросу {}", ex.getMessage(), request);
        return handleExceptionInternal(ex, DEFAULT_RESPONSE, null, HttpStatus.OK, request);
    }
}
