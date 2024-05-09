package com.rangers.medicineservice.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class CreatedCartItemDto {
    UUID cartItemId;
    MedicineDto medicine;
    UserDto user;
    int quantity;
}
