package com.rangers.medicineservice.exception;

public class NotEnoughBalanceExp extends RuntimeException {
    public NotEnoughBalanceExp(String message) {
        super(message);
    }
}
