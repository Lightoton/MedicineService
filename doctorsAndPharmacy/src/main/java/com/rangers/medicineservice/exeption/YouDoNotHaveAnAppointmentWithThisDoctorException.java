package com.rangers.medicineservice.exeption;

public class YouDoNotHaveAnAppointmentWithThisDoctorException extends BadRequestException {
    public YouDoNotHaveAnAppointmentWithThisDoctorException(String message) {
        super(message);
    }

}
