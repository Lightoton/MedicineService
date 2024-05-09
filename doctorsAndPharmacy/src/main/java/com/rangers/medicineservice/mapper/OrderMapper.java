package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.OrderDto;
import com.rangers.medicineservice.dto.UserHistoryOrderDetailsDto;
import com.rangers.medicineservice.dto.UserHistoryOrdersDto;
import com.rangers.medicineservice.entity.Order;
import com.rangers.medicineservice.entity.OrderDetail;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE) //unmappedTargetPolicy = ReportingPolicy.IGNORE
@Component
public interface OrderMapper {
    OrderDto toDto(Order order);

    List<UserHistoryOrdersDto> toUserHistoryOrdersDto(List<Order> orders);
    @AfterMapping
    default void getOrderDetails(@MappingTarget UserHistoryOrdersDto userHistoryOrdersDto,
                                 Order order) {
        userHistoryOrdersDto.setOrderId(order.getOrderId());
        userHistoryOrdersDto.setOrderDate(order.getOrderDate());
    }
}
