package com.example.scheduler.exception;

import com.example.scheduler.exception.ex.GeneralException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleIllegalState(IllegalStateException ex) {
        CustomErrorResponse<Void> body = CustomErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Illegal State", null);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleGeneral(GeneralException ex) {
        HttpStatus status = ex.getStatus();
        CustomErrorResponse<Void> body = CustomErrorResponse.of(status.value(), ex.getMessage(), null);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> {
                            String[] pathParts = violation.getPropertyPath().toString().split("\\.");
                            return pathParts[pathParts.length - 1];
                        },
                        ConstraintViolation::getMessage
                ));

        CustomErrorResponse<Map<String, String>> body = CustomErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "Invalid input", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (oldMsg, newMsg) -> oldMsg + ", " + newMsg
                ));

        CustomErrorResponse<Map<String, String>> body = CustomErrorResponse.of(status.value(), "Validation Failed", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "The HTTP method " + ex.getMethod() + " is not supported for this endpoint";
        CustomErrorResponse<Void> body = CustomErrorResponse.of(status.value(), message, null);
        return new ResponseEntity<>(body, headers, status);
    }
}
