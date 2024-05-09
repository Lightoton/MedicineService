package com.rangers.medicineservice.dto;

import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
    //private List<Integer> quantities;
    private List<OrderDetail> orderDetails;
    //private  List<UserHistoryOrderDetailsDto> userHistoryOrderDetailsDtos;
    /*
    Medicine.class
    */
//    private String name;
//    private BigDecimal price;

}
