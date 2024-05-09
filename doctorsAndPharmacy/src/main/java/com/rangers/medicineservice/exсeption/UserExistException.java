package com.rangers.medicineservice.exсeption;

public class UserExistException extends RuntimeException {
    public UserExistException (String message) {
        super(message);
    }
}
