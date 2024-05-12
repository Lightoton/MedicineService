package com.rangers.medicineservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UserHistoryOrdersDto {
    /*
    Order.class
    */
    private UUID orderId;
    private LocalDate orderDate;
    /*
    OrderDetail.class
    */
    List<UserHistoryOrderDetailsDto> userHistoryOrderDetailsDtoList;
}
