package com.nhnacademy.minidooray.advice;

import com.nhnacademy.minidooray.exception.AccountAlreadyExistsException;
import com.nhnacademy.minidooray.exception.AccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<String> handleAlreadyExistsException(Exception ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
    }
}
