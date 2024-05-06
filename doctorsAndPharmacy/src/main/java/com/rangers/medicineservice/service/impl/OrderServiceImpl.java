package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.OrderDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.*;
import com.rangers.medicineservice.mapper.OrderMapper;
import com.rangers.medicineservice.repository.MedicineRepository;
import com.rangers.medicineservice.repository.OrderRepository;
import com.rangers.medicineservice.repository.PrescriptionRepository;
import com.rangers.medicineservice.repository.UserRepository;
import com.rangers.medicineservice.service.interf.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrdersService {
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderDto addOrder(PrescriptionDto prescriptionDto) {
        Order order = orderMapper.toEntity(prescriptionDto);
        Optional<User> user = userRepository.findById(UUID.fromString(prescriptionDto.getUserId()));
        order.setUser(user.orElse(null));

//        order.setDeliveryAddress(order.getUser().getAddress());

        Optional<Prescription> prescription = prescriptionRepository.findById(UUID.fromString(prescriptionDto.getPrescriptionId()));
        order.setPrescription(prescription.orElse(null));

        order.setOrderDate(LocalDate.now());

        BigDecimal cost = BigDecimal.ZERO;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            orderDetail.setOrder(order);
            Medicine medicine = medicineRepository.findMedicineByMedicineId(orderDetail.getMedicine().getMedicineId());
            orderDetail.setMedicine(medicine);
            orderDetail.setQuantity(orderDetail.getQuantity());
            cost = cost.add(BigDecimal.valueOf(orderDetail.getQuantity())
                    .multiply(orderDetail.getMedicine().getPrice()));
        }
        order.setOrderCost(cost);

        if(prescriptionDto.getDeliveryAddress()!= null){
            order.setDeliveryAddress(prescriptionDto.getDeliveryAddress());
        } else {
            OrderDetail orderDetail = order.getOrderDetails().stream().findFirst().orElse(null);
            assert orderDetail != null;
            order.setDeliveryAddress(orderDetail.getMedicine().getPharmacy().getAddress());
        }

        Order save = orderRepository.saveAndFlush(order);

        return orderMapper.toDto(save);
    }
}
