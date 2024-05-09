package com.rangers.medicineservice.exception;

public class UserDoesNotExistException extends ObjectDoesNotExistException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
