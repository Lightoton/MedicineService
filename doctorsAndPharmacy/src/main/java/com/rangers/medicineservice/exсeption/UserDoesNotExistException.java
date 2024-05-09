package com.rangers.medicineservice.exсeption;

public class UserDoesNotExistException extends ObjectDoesNotExistException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
