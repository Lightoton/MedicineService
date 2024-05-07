package com.rangers.medicineservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserHistoryOrdersDto {
    /*
    User.class
    */
//    private UUID userId;
//    private String firstname;
//    private String lastname;
    /*
    Order.class
    */
    private UUID orderId;
    private LocalDate orderDate;
    /*
    OrderDetail.class
    */
    private Integer quantity;
    /*
    Medicine.class
    */
    private String name;
    private BigDecimal price;
}
