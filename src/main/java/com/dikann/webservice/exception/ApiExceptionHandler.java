package com.dikann.webservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(ObjectNotFoundException e) {
        HttpStatus badReq = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getCode(), e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(apiException, badReq);
    }

    @ExceptionHandler(value = {ObjectTakenException.class})
    public ResponseEntity<Object> handleObjectTakenException(ObjectTakenException e) {
        HttpStatus badReq = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getCode(), e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(apiException, badReq);
    }

}
