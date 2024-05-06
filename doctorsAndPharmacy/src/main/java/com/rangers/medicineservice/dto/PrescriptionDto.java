package com.rangers.medicineservice.dto;

import com.rangers.medicineservice.entity.Medicine;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PrescriptionDto {
    String prescriptionId;
    String userId;
    LocalDate expiryDate;
    String deliveryAddress;
    List<Medicine> medicines;
}
