package com.rangers.medicineservice.dto;

import lombok.Value;

@Value
public class CartItemBeforeCreationDto {
    String medicineId;
    String userId;
    int quantity;
}

