package com.rangers.medicineservice.controller;


import com.rangers.medicineservice.annotation.CreateOrder;
import com.rangers.medicineservice.annotation.GetAllOrders;
import com.rangers.medicineservice.dto.OrderFromPrescriptionDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Order;
import com.rangers.medicineservice.service.interf.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class StockingPrescriptionController {

    private final OrderService ordersService;

    @CreateOrder(path = "/add")
    public OrderFromPrescriptionDto addOrder(@RequestBody PrescriptionDto prescriptionDto) {
        return ordersService.addOrder(prescriptionDto);
    }

    @GetAllOrders(path = "/all")
    public List<Order> showAllOrders() {
        return ordersService.showAllOrders();
    }
}
