package com.rangers.medicineservice.service;

import com.rangers.medicineservice.dto.CartItemToOrderDetailDto;
import com.rangers.medicineservice.dto.CreatedOrderDto;
import com.rangers.medicineservice.dto.OrderBeforeCreation;
import com.rangers.medicineservice.entity.CartItem;

public interface OrderService {
    CreatedOrderDto createOrder(CartItem cartItem);

//    OrderBeforeCreation toOrderBeforeCreation(CartItemToOrderDetailDto cartItemToOrderDetailDto);

//    CartItemToOrderDetailDto createOrderDetail(CartItem cartItem);

}
