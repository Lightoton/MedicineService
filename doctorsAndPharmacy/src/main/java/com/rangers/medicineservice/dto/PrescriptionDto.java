package com.rangers.medicineservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrescriptionDto {
    String prescriptionId;
    String userId;
    LocalDate expiryDate;
    String deliveryAddress;

    @Override
    public String toString() {
        return "prescription № " + prescriptionId + ", " + expiryDate;
    }
}
