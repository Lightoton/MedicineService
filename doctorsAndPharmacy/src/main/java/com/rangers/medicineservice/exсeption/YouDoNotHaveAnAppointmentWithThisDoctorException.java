package com.rangers.medicineservice.exсeption;

public class YouDoNotHaveAnAppointmentWithThisDoctorException extends BadRequestException {
    public YouDoNotHaveAnAppointmentWithThisDoctorException(String message) {
        super(message);
    }

}
