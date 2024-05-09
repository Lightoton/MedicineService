package com.rangers.medicineservice.exсeption;

public class ScheduleDoesNotHaveDoctorException extends BadRequestException {
    public ScheduleDoesNotHaveDoctorException(String message) {
        super(message);
    }
}
