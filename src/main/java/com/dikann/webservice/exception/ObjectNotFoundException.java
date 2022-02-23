package com.dikann.webservice.exception;

import com.dikann.webservice.utils.ApplicationConst;

public class ObjectNotFoundException extends RuntimeException {
    private final String code = ApplicationConst.errorObjectNotFoundCode;

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }
}
