package com.rangers.medicineservice.service;

import com.rangers.medicineservice.dto.CartItemBeforeCreationDto;
import com.rangers.medicineservice.dto.CreatedCartItemDto;

import java.util.UUID;

public interface CartItemService {
    CreatedCartItemDto createCartItem(CartItemBeforeCreationDto cartItemDto);
    void deleteCartItem(UUID cartItemId);
}
