package com.example.scheduler.exception.ex;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GeneralException{
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
