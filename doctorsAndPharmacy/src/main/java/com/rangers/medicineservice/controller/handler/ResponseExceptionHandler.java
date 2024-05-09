package com.rangers.medicineservice.controller.handler;

import com.rangers.medicineservice.exception.DataNotExistExp;
import com.rangers.medicineservice.exception.InActivePrescriptionExp;
import com.rangers.medicineservice.exception.NotEnoughBalanceExp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(DataNotExistExp.class)
    public ResponseEntity<?> handleNoDataFoundException(DataNotExistExp e) {
        ErrorExtension body = new ErrorExtension(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        if (e.getMessage().equals("The prescription is empty")) {
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
        if (e.getMessage().equals("The prescription belongs to another user")) {
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @ExceptionHandler(InActivePrescriptionExp.class)
    public ResponseEntity<?> handleInactivePrescriptionException(InActivePrescriptionExp e) {
        ErrorExtension body = new ErrorExtension(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughBalanceExp.class)
    public ResponseEntity<?> handleNegativeBalancePrescriptionException(NotEnoughBalanceExp e) {
        ErrorExtension body = new ErrorExtension(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
