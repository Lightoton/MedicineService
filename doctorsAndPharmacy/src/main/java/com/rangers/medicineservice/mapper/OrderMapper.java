package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.OrderDto;
import com.rangers.medicineservice.entity.Order;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface OrderMapper {
    OrderDto toDto(Order order);

//    @Mappings({
//            @Mapping(target = "orderId", source = "orderId"),
//            @Mapping(target = "orderDate", source = "orderDate"),
//            @Mapping(target = "userHistoryOrdersDto.quantity", source = "orders.orderDetails.quantity"),
//            @Mapping(target = "userHistoryOrdersDto.name", source = "orders.orderDetails.medicine.name"),
//            @Mapping(target = "userHistoryOrdersDto.price", source = "orders.orderDetails.medicine.price"),
//    })
    //List<UserHistoryOrdersDto> toUserHistoryOrdersDto(List<Order> orders);
}
