package com.rangers.medicineservice.exception;

public class UserExistException extends RuntimeException {
    public UserExistException (String message) {
        super(message);
    }
}
