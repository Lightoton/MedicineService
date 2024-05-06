package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.dto.ErrorExtension;
import com.rangers.medicineservice.exeption.ErrorCode;
import com.rangers.medicineservice.exeption.QuantityCantBeLowerThenOneException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(QuantityCantBeLowerThenOneException.class)
    public ResponseEntity<ErrorExtension> handleInvalidValueException(Exception e) {
        return new ResponseEntity<>(new ErrorExtension(
                e.getMessage(),
                ErrorCode.INVALID_VALUE
        ), HttpStatus.BAD_REQUEST);
    }
}
