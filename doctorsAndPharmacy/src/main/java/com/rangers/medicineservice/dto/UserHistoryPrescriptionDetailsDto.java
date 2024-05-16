package com.rangers.medicineservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserHistoryPrescriptionDetailsDto {
    private String medicineName;
    private int quantity;
    private BigDecimal medicinePrice;
}
