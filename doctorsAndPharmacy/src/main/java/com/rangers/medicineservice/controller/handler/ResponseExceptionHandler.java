package com.rangers.medicineservice.controller.handler;

import com.rangers.medicineservice.exeption.BadRequestException;
import com.rangers.medicineservice.exeption.ObjectDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(ObjectDoesNotExistException.class)
    public ResponseEntity<ErrorExtension> handleObjectDoesNotExistException(ObjectDoesNotExistException ex) {
        ErrorExtension body = new ErrorExtension(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorExtension> handleBadRequestException(BadRequestException ex) {
        ErrorExtension body = new ErrorExtension(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorExtension> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorExtension body = new ErrorExtension(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


}
