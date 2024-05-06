package com.rangers.medicineservice.dto;

import com.rangers.medicineservice.entity.OrderDetail;
import com.rangers.medicineservice.entity.Pharmacy;
import com.rangers.medicineservice.entity.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderBeforeCreation {
    User user;
    List<OrderDetail> orderDetails;
    BigDecimal orderCost;
    String deliveryAddress;
    Pharmacy pharmacy;
}
