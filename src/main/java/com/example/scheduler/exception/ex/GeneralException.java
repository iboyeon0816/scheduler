package com.example.scheduler.exception.ex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

}
