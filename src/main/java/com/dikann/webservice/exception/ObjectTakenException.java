package com.dikann.webservice.exception;

import com.dikann.webservice.utils.ApplicationConst;

public class ObjectTakenException extends RuntimeException {
    private final String code = ApplicationConst.errorObjectTakenCode;

    public ObjectTakenException(String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }
}
