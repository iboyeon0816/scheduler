package com.example.scheduler.exception.ex;

import org.springframework.http.HttpStatus;

public class UnAuthenticatedException extends GeneralException{
    public UnAuthenticatedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
