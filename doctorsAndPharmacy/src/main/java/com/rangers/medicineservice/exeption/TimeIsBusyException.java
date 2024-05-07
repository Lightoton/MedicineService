package com.rangers.medicineservice.exeption;

public class TimeIsBusyException extends BadRequestException {
    public TimeIsBusyException(String message) {
        super(message);
    }
}
