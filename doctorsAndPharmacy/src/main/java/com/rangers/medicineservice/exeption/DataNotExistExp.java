package com.rangers.medicineservice.exeption;

public class DataNotExistExp extends RuntimeException {
    public DataNotExistExp(String message) {
        super(message);
    }
}
