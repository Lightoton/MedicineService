package com.rangers.medicineservice.exeption;

public class ScheduleDoesNotHaveDoctorException extends BadRequestException {
    public ScheduleDoesNotHaveDoctorException(String message) {
        super(message);
    }
}
