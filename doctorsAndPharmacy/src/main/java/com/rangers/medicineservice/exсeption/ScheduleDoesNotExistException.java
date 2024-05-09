package com.rangers.medicineservice.exсeption;

public class ScheduleDoesNotExistException  extends ObjectDoesNotExistException {
    public ScheduleDoesNotExistException(String message) {
        super(message);
    }
}