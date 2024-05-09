package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.CreatedOrderDto;
import com.rangers.medicineservice.dto.OrderFromPrescriptionDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.Order;

import java.util.List;
import java.util.Set;

public interface OrderService {
    CreatedOrderDto createOrder(Set<CartItem> cartItems);

    OrderFromPrescriptionDto addOrder(PrescriptionDto prescriptionDto);

    List<Order> showAllOrders();

}
