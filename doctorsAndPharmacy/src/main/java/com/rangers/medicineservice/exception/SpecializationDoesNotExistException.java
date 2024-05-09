package com.rangers.medicineservice.exception;

public class SpecializationDoesNotExistException extends IllegalArgumentException {
    public SpecializationDoesNotExistException(String message) {
        super(message);
    }
}
