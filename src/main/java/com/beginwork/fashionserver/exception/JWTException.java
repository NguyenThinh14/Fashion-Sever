package com.beginwork.fashionserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class JWTException extends RuntimeException {
    public JWTException(String message) {
        super(message);
    }
}