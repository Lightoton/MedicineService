package com.rangers.medicineservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescriptionDto that = (PrescriptionDto) o;
        return Objects.equals(prescriptionId, that.prescriptionId) && Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionId, expiryDate);
    }
}
