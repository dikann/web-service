package com.dikann.webservice.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiException {
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime time;

    public ApiException(String code, String message, HttpStatus httpStatus, LocalDateTime time) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
