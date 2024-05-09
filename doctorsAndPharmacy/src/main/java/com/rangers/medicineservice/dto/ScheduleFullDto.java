package com.rangers.medicineservice.dto;

import lombok.Data;

@Data
public class ScheduleFullDto {
    String dateAndTime;
    String status;
    String appointmentType;
    String link;
    String doctorName;
    String doctorSpecialization;
}
