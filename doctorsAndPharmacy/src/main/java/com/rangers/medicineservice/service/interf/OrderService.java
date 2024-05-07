package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.CreatedOrderDto;
import com.rangers.medicineservice.entity.CartItem;

import java.util.Set;

public interface OrderService {
    CreatedOrderDto createOrder(Set<CartItem> cartItems);

//    OrderBeforeCreation toOrderBeforeCreation(CartItemToOrderDetailDto cartItemToOrderDetailDto);

//    CartItemToOrderDetailDto createOrderDetail(CartItem cartItem);

}
