package com.rangers.medicineservice.dto;

import com.rangers.medicineservice.entity.User;
import lombok.Value;

@Value
public class CartItemToOrderDetailDto {
    Integer quantity;
    MedicineDto medicine;
    UserDto user;
}
