package com.rangers.medicineservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Data
public class CreateVisitResponseDto {
    final String answer = "Your visit was created";
    String dateTime;
    String doctorName;
    String userName;
    String linkOrAddress;

    @Override
    public String toString() {
        return  getUserName() + ", your appointment has created. Doctor: "
                + getDoctorName() + ". Date: " + getDateTime();
    }
}
