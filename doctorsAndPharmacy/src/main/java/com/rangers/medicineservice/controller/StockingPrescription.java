package com.rangers.medicineservice.controller;


import com.rangers.medicineservice.annotation.CreateOrder;
import com.rangers.medicineservice.dto.OrderDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.service.interf.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class StockingPrescription {
    private final OrdersService ordersService;

    @CreateOrder(path = "/add")
    public OrderDto addOrder(@RequestBody PrescriptionDto prescriptionDto) {
        return ordersService.addOrder(prescriptionDto);
    }
}
