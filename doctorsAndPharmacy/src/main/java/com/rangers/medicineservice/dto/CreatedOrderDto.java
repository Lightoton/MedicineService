package com.rangers.medicineservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rangers.medicineservice.entity.OrderDetail;
import com.rangers.medicineservice.entity.Pharmacy;
import com.rangers.medicineservice.entity.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreatedOrderDto {
    String orderId;
    UserDto user;
    List<OrderDetail> orderDetails;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate orderDate;
    OrderStatus status;
    BigDecimal orderCost;
    String deliveryAddress;
    Pharmacy pharmacy;

}
