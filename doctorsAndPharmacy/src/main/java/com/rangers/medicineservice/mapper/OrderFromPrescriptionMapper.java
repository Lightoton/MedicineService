package com.rangers.medicineservice.mapper;


import com.rangers.medicineservice.dto.OrderDetailDto;
import com.rangers.medicineservice.dto.OrderFromPrescriptionDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.*;
import com.rangers.medicineservice.entity.enums.OrderStatus;
import com.rangers.medicineservice.mapper.util.GetNameFromUserUtil;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {GetNameFromUserUtil.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderFromPrescriptionMapper {
    @Mappings({
            @Mapping(target = "user", expression = "java(GetNameFromUserUtil.getName(order))"),
            @Mapping(target = "address", source = "deliveryAddress")
    })
    OrderFromPrescriptionDto toDto(Order order);

    @AfterMapping
    default void getInfo(@MappingTarget OrderDetailDto orderDetailDto, OrderDetail orderDetail) {
        orderDetailDto.setName(orderDetail.getMedicine().getName());
        orderDetailDto.setPrice(orderDetail.getMedicine().getPrice());
    }

    Order toEntity(List<PrescriptionDetail> details, PrescriptionDto prescriptionDto);

    default List<OrderDetail> getOrderDetails(List<PrescriptionDetail> details) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (PrescriptionDetail detail : details) {
            Medicine medicine = new Medicine();
            medicine.setMedicineId(detail.getMedicine().getMedicineId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setMedicine(medicine);
            orderDetail.setQuantity(detail.getQuantity());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    @AfterMapping
    default void createOrder(@MappingTarget Order order, PrescriptionDto prescriptionDto, List<PrescriptionDetail> details) {
        order.setStatus(OrderStatus.CREATED);
        User user = new User();
        user.setUserId(UUID.fromString(prescriptionDto.getUserId()));
        order.setUser(user);
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(prescription.getPrescriptionId());
        order.setPrescription(prescription);
        order.setOrderDetails(getOrderDetails(details));
    }
}
