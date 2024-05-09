package com.rangers.medicineservice.exсeption;

public class TimeIsBusyException extends BadRequestException {
    public TimeIsBusyException(String message) {
        super(message);
    }
}
