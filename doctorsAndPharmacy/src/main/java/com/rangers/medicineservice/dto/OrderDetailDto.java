package com.rangers.medicineservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailDto {
    String name;
    String quantity;
    BigDecimal price;
}
