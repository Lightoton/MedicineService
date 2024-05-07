package com.rangers.medicineservice.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (String message) {
        super(message);
    }
}
