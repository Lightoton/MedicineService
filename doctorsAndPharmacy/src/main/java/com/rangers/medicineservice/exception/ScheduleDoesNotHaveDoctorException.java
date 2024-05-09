package com.rangers.medicineservice.exception;

public class ScheduleDoesNotHaveDoctorException extends BadRequestException {
    public ScheduleDoesNotHaveDoctorException(String message) {
        super(message);
    }
}
