package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.exeption.DataNotExistExp;
import com.rangers.medicineservice.exeption.InActivePrescriptionExp;
import com.rangers.medicineservice.exeption.NotEnoughBalanceExp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResponseExceptionHandler {

//    @ExceptionHandler({DataNotExistExp.class, NullPointerException.class,
//            InActivePrescriptionExp.class, NotEnoughBalanceExp.class})
//    public ResponseEntity<ErrorExtension> handleNoDataFoundException(Exception exp) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
//    }
@ExceptionHandler(DataNotExistExp.class)
public ResponseEntity<List<Object>> handleNoDataFoundException(DataNotExistExp e) {
    ErrorExtension body = new ErrorExtension(e.getMessage(),HttpStatus.NOT_FOUND.value());
    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
}
}
