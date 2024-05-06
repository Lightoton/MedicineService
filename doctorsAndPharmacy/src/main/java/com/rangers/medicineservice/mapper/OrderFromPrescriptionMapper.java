package com.rangers.medicineservice.mapper;


import com.rangers.medicineservice.dto.OrderDetailDto;
import com.rangers.medicineservice.dto.OrderDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.*;
import com.rangers.medicineservice.entity.enums.OrderStatus;
import com.rangers.medicineservice.mapper.util.GetNameFromUserUtil;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = {GetNameFromUserUtil.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderFromPrescriptionMapper {
    @Mappings({
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "user",  expression = "java(GetNameFromUserUtil.getName(order))"),
            @Mapping(target = "address", source = "deliveryAddress")

    })
    OrderDto toDto(Order order);

    @AfterMapping
    default void genInfo(@MappingTarget OrderDetailDto orderDetailDto, OrderDetail orderDetail){

        orderDetailDto.setName(orderDetail.getMedicine().getName());
        orderDetailDto.setPrice(orderDetail.getMedicine().getPrice());


    }

    Order toEntity(PrescriptionDto prescriptionDto);

    default List<OrderDetail> getOrderDetails(PrescriptionDto prescriptionDto) {
        Map<UUID, Long> counting = prescriptionDto.getMedicines().stream()
                .collect(Collectors.groupingBy(Medicine::getMedicineId, Collectors.counting()));
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Map.Entry<UUID, Long> entry : counting.entrySet()) {
            Medicine medicine = new Medicine();
            medicine.setMedicineId(entry.getKey());
            int quantity = entry.getValue().intValue();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setMedicine(medicine);
            orderDetail.setQuantity(quantity);
            orderDetails.add(orderDetail);
        }

        return orderDetails;
    }

    @AfterMapping
    default void createOrder(@MappingTarget Order order, PrescriptionDto prescriptionDto) {
        order.setStatus(OrderStatus.CREATED);
//        prescriptionDto.getDeliveryAddress()==null ? order.se
        User user = new User();
        user.setUserId(UUID.fromString(prescriptionDto.getUserId()));
        order.setUser(user);
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(prescription.getPrescriptionId());
        order.setPrescription(prescription);
        order.setOrderDetails(getOrderDetails(prescriptionDto));

    }
}
