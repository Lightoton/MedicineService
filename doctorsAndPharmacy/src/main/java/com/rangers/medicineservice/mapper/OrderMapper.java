package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.OrderDto;
import com.rangers.medicineservice.dto.UserHistoryOrderDetailsDto;
import com.rangers.medicineservice.dto.UserHistoryOrdersDto;
import com.rangers.medicineservice.dto.CartItemToOrderDetailDto;
import com.rangers.medicineservice.dto.CreatedOrderDto;
import com.rangers.medicineservice.dto.OrderBeforeCreation;
import com.rangers.medicineservice.entity.Order;
import com.rangers.medicineservice.entity.OrderDetail;
import com.rangers.medicineservice.entity.enums.OrderStatus;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {LocalDate.class, OrderStatus.class, Order.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface OrderMapper {


    @Mapping(target = "orderDetailId", ignore = true)
    @Mapping(target = "quantity", source = "cartItemDto.quantity")
    @Mapping(target = "medicine", source = "medicine")
    OrderDetail toOrderDetail(CartItemToOrderDetailDto cartItemDto);

    @Mapping(target = "orderDetails", expression = "java(new ArrayList<>())")
    @Mapping(target = "deliveryAddress", ignore = true)
    @Mapping(target = "pharmacy", ignore = true)
    OrderBeforeCreation toOrderBeforeCreation(OrderDetail orderDetail);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "orderDetails", source = "orderDetails")
    @Mapping(target = "orderDate", expression = "java(LocalDate.now())")
    @Mapping(target = "status", expression = "java(OrderStatus.CREATED)")
    @Mapping(target = "orderCost", source = "orderBeforeCreation.orderCost")
    @Mapping(target = "deliveryAddress", ignore = true)
    @Mapping(target = "pharmacy", ignore = true)
    CreatedOrderDto toDto(OrderBeforeCreation orderBeforeCreation);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "orderDetails", source = "orderDetails")
    @Mapping(target = "orderDate", source = "orderDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "orderCost", source = "orderCost")
    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    @Mapping(target = "pharmacy", source = "pharmacy")
    Order toEntity(CreatedOrderDto createdOrderDto);

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
