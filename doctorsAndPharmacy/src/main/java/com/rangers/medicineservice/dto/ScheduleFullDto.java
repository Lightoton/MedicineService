package com.rangers.medicineservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ScheduleFullDto {
    UUID scheduleId;
    String dateAndTime;
    String status;
    String appointmentType;
    String link;
    String doctorName;
    String doctorSpecialization;
}
