package com.rangers.medicineservice.exeption;

public class NotEnoughBalanceExp extends RuntimeException {
    public NotEnoughBalanceExp(String message) {
        super(message);
    }
}
