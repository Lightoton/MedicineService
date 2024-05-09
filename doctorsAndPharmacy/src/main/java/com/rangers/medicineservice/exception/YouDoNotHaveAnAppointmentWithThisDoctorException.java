package com.rangers.medicineservice.exception;

public class YouDoNotHaveAnAppointmentWithThisDoctorException extends BadRequestException {
    public YouDoNotHaveAnAppointmentWithThisDoctorException(String message) {
        super(message);
    }

}
