package com.rangers.medicineservice.exception;

public class PrescriptionNotFoundException extends ObjectDoesNotExistException {
    public PrescriptionNotFoundException(String message) {
        super(message);
    }
}
