package com.rangers.medicineservice.dto;

import com.rangers.medicineservice.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderBeforeCreation {
    UserDto user;
    List<OrderDetail> orderDetails;
    BigDecimal orderCost;
    String deliveryAddress;
    String pharmacy;
}
