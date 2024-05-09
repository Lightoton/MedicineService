package com.rangers.medicineservice.exception;

public class DataNotExistExp extends RuntimeException {
    public DataNotExistExp(String message) {
        super(message);
    }
}
