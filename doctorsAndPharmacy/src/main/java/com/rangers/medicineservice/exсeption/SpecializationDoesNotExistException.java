package com.rangers.medicineservice.exсeption;

public class SpecializationDoesNotExistException extends IllegalArgumentException {
    public SpecializationDoesNotExistException(String message) {
        super(message);
    }
}
