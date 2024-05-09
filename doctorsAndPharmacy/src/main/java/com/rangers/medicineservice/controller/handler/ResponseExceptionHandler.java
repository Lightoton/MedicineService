package com.rangers.medicineservice.controller.handler;


import com.rangers.medicineservice.exception.DataNotExistExp;
import com.rangers.medicineservice.exception.InActivePrescriptionExp;
import com.rangers.medicineservice.exception.NotEnoughBalanceExp;
import com.rangers.medicineservice.exception.ScheduleNotFoundException;
import com.rangers.medicineservice.exception.BadRequestException;
import com.rangers.medicineservice.exception.ObjectDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.annotation.Description;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ErrorExtension> handleScheduleNotFound(Exception ex){

        return new ResponseEntity<>(new ErrorExtension(
                ex.getMessage(), HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ObjectDoesNotExistException.class)
    public ResponseEntity<ErrorExtension> handleObjectDoesNotExistException(ObjectDoesNotExistException ex) {
        ErrorExtension body = new ErrorExtension(
                ex.getMessage(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorExtension> handleBadRequestException(BadRequestException ex) {
        ErrorExtension body = new ErrorExtension(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorExtension> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorExtension body = new ErrorExtension(ex.getMessage(),
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
  
    @ExceptionHandler(DataNotExistExp.class)
      public ResponseEntity<?> handleNoDataFoundException(DataNotExistExp e) {
          ErrorExtension body = new ErrorExtension(e.getMessage(), HttpStatus.BAD_REQUEST);
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
          ErrorExtension body = new ErrorExtension(e.getMessage(), HttpStatus.BAD_REQUEST);
          return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
      }

      @ExceptionHandler(NotEnoughBalanceExp.class)
      public ResponseEntity<?> handleNegativeBalancePrescriptionException(NotEnoughBalanceExp e) {
          ErrorExtension body = new ErrorExtension(e.getMessage(), HttpStatus.BAD_REQUEST);
          return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
      }

}
