package com.rangers.medicineservice.exeption;

public class ObjectDoesNotExistException extends RuntimeException{
    public ObjectDoesNotExistException(String message) {
        super(message);
    }
}
