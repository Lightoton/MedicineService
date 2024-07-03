package com.rangers.medicineservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserHistoryPrescriptionsDto {
    private LocalDate createdAt;
    private LocalDate expDate;
    private boolean isActive;
    private String doctorName;
    private List<UserHistoryPrescriptionDetailsDto> userHistoryPrescriptionDetailsDtoList;
}
