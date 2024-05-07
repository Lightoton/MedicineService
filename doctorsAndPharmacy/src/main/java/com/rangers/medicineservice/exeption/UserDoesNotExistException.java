package com.rangers.medicineservice.exeption;

public class UserDoesNotExistException extends ObjectDoesNotExistException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
