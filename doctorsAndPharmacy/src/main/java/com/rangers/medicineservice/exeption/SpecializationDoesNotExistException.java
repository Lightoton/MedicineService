package com.rangers.medicineservice.exeption;

public class SpecializationDoesNotExistException extends IllegalArgumentException {
    public SpecializationDoesNotExistException(String message) {
        super(message);
    }
}
