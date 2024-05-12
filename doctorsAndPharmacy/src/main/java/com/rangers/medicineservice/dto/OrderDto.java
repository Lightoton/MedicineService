package com.rangers.medicineservice.dto;


import com.rangers.medicineservice.entity.OrderDetail;
import com.rangers.medicineservice.entity.Pharmacy;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Data
public class OrderDto {
    private UUID orderId;
    private Prescription prescription;
    private List<OrderDetail> orderDetails;
    private LocalDate orderDate;
    private OrderStatus status;
    private BigDecimal orderCost;
    private String deliveryAddress;
    private Pharmacy pharmacy;

}
