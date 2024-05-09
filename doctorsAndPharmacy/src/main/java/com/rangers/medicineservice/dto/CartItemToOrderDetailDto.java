package com.rangers.medicineservice.dto;

import lombok.Value;

@Value
public class CartItemToOrderDetailDto {
    Integer quantity;
    MedicineDto medicine;
    UserDto user;
}
