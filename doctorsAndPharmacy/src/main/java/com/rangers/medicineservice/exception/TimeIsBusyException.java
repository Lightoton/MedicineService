package com.rangers.medicineservice.exception;

public class TimeIsBusyException extends BadRequestException {
    public TimeIsBusyException(String message) {
        super(message);
    }
}
