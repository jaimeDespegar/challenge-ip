package com.example.app.controllers;

import com.example.app.exceptions.*;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class AdvisorController {

    @ExceptionHandler({ ClientException.class })
    public ResponseEntity<Object> handleCityNotFoundException(RuntimeException re, WebRequest request) {
        return buildResponseEntity(re, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ IpNotAllowedToConsultException.class, HandlerException.class })
    public ResponseEntity<Object> handleIpBlockedException(RuntimeException re, WebRequest request) {
        return buildResponseEntity(re, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity buildResponseEntity(RuntimeException re, HttpStatus status) {
        Map<String, Object> body = Maps.newHashMap();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", re.getMessage());
        body.put("status", status.value());

        return new ResponseEntity<>(body, status);
    }

}