package com.rangers.medicineservice.exception;

public class ObjectDoesNotExistException extends RuntimeException{
    public ObjectDoesNotExistException(String message) {
        super(message);
    }
}
