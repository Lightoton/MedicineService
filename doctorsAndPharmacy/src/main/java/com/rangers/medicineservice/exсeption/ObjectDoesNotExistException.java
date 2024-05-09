package com.rangers.medicineservice.exсeption;

public class ObjectDoesNotExistException extends RuntimeException{
    public ObjectDoesNotExistException(String message) {
        super(message);
    }
}
