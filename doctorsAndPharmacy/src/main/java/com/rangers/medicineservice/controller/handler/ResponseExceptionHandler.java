package com.rangers.medicineservice.controller.handler;

import com.rangers.medicineservice.exception.ScheduleNotFoundException;
import com.rangers.medicineservice.exception.BadRequestException;
import com.rangers.medicineservice.exception.ObjectDoesNotExistException;
import com.rangers.medicineservice.exception.UserNotFoundException;
import com.rangers.medicineservice.exception.UserExistException;
import com.rangers.medicineservice.exception.OrderNotFoundException;
import com.rangers.medicineservice.exception.PrescriptionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ScheduleNotFoundException.class, UserNotFoundException.class, OrderNotFoundException.class,
            ScheduleNotFoundException.class,PrescriptionNotFoundException.class})
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

    @ExceptionHandler({BadRequestException.class, UserExistException.class})
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

}
