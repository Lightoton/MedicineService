package com.rangers.medicineservice.dto;

import lombok.Data;

@Data
public class CancelVisitResponseDto {
    final String answer = "Your visit was canceled";
    String userFullName;
    String dateTime;
    String doctorFullName;

    @Override
    public String toString() {
        return "Your appointment was canceled." +
                "userFullName='" + userFullName + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", doctorFullName='" + doctorFullName + '\'' +
                '}';
    }
}
