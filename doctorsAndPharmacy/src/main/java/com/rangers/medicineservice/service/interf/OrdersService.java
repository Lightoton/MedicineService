package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.OrderDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Order;

import java.util.List;

public interface OrdersService {
    OrderDto addOrder(PrescriptionDto prescriptionDto);

    List<Order> showAllOrders();
}
