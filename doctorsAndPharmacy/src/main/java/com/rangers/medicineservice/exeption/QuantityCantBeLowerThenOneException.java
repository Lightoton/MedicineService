package com.rangers.medicineservice.exeption;

public class QuantityCantBeLowerThenOneException extends RuntimeException {
    public QuantityCantBeLowerThenOneException(String message) {super(message);}
}
