package com.rangers.medicineservice.exception;

public class QuantityCantBeLowerThenOneException extends RuntimeException {
    public QuantityCantBeLowerThenOneException(String message) {
        super(message);
    }
}
