package com.rangers.medicineservice.exeption;

public class ScheduleDoesNotExistException  extends ObjectDoesNotExistException {
    public ScheduleDoesNotExistException(String message) {
        super(message);
    }
}