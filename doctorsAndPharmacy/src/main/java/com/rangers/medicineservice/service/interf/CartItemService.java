package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.CartItemBeforeCreationDto;
import com.rangers.medicineservice.dto.CreatedCartItemDto;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.User;

import java.util.List;
import java.util.UUID;

public interface CartItemService {
    CreatedCartItemDto createCartItem(CartItemBeforeCreationDto cartItemDto);
    void deleteCartItem(UUID cartItemId);

    List<CartItem> getCartItemsByUserId(String id);

    void deleteAllByMedicineAndUser(String medicineId, String userId);
    List<CartItem> getAllByMedicineAndUser(Medicine medicine, User user);
}
