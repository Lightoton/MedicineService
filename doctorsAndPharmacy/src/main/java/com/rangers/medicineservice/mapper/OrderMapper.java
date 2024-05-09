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
        unmappedTargetPolicy = ReportingPolicy.IGNORE) //, uses = {OrderDetailsMapper.class}
@Component
public interface OrderMapper {
    OrderDto toDto(Order order);
    List<UserHistoryOrdersDto> toUserHistoryOrdersDto(List<Order> orders);

    @Mappings({
            @Mapping(target = "name", source = "orderDetail.medicine.name"),
            @Mapping(target = "price", source = "orderDetail.medicine.price")
    })
    UserHistoryOrderDetailsDto mapToUserHistoryOrderDetailsDto(OrderDetail orderDetail);

    @AfterMapping
    default void getOrderDetails(@MappingTarget UserHistoryOrdersDto userHistoryOrdersDto,
                                 Order order) {

        List<UserHistoryOrderDetailsDto> userHistoryOrderDetailsDtoList = order.getOrderDetails().stream()
                .map(this::mapToUserHistoryOrderDetailsDto)
                .toList();
        userHistoryOrdersDto.setUserHistoryOrderDetailsDtoList(userHistoryOrderDetailsDtoList);
    }
}
