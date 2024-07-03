package com.rangers.medicineservice.dto;


import lombok.Data;

import java.util.List;

@Data
public class OrderFromPrescriptionDto {
    String status;
    String user;
    String orderCost;
    String orderDate;
    String address;
    List<OrderDetailDto> orderDetails;

    @Override
    public String toString() {
        return "Order created successfully. Total cost: " + orderCost;
    }
}
