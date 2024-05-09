package com.rangers.medicineservice.dto;

import lombok.Data;

@Data
public class PrescriptionDto {
    String prescriptionId;
    String expDate;
    String createdAt;
    boolean isActive;
    String doctorName;
}
