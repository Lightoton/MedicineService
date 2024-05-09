package com.rangers.medicineservice.exception;

public class ScheduleDoesNotExistException  extends ObjectDoesNotExistException {
    public ScheduleDoesNotExistException(String message) {
        super(message);
    }
}