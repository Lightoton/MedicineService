package com.rangers.medicineservice.dto;

import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.User;
import lombok.Value;

import java.util.UUID;

@Value
public class CreatedCartItemDto {
    UUID cartItemId;
    Medicine medicine;
    User user;
    int quantity;
}
