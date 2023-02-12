package com.github.kardzhaliyski.collaboration.controllers;

import com.github.kardzhaliyski.collaboration.exceptions.InvalidDataException;
import com.github.kardzhaliyski.collaboration.exceptions.InvalidDateFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CollaborationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidDateFormatException.class, InvalidDataException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
