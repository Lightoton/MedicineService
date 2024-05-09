package com.rangers.medicineservice.exception;

public class InActivePrescriptionExp extends RuntimeException {
    public InActivePrescriptionExp(String message) {
        super(message);
    }
}
