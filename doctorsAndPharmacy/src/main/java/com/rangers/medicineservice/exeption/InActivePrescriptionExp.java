package com.rangers.medicineservice.exeption;

public class InActivePrescriptionExp extends RuntimeException {
    public InActivePrescriptionExp(String message) {
        super(message);
    }
}
