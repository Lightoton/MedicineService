package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.OrderDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.*;
import com.rangers.medicineservice.exeption.DataNotExistExp;
import com.rangers.medicineservice.exeption.InActivePrescriptionExp;
import com.rangers.medicineservice.exeption.NotEnoughBalanceExp;
import com.rangers.medicineservice.exeption.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.OrderFromPrescriptionMapper;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrdersService {
    private final OrderFromPrescriptionMapper orderFromPrescriptionMapper;
    private final UserRepository userRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderDto addOrder(PrescriptionDto prescriptionDto) {
        Order order = orderFromPrescriptionMapper.toEntity(prescriptionDto);

        Optional<User> user = userRepository.findById(UUID.fromString(prescriptionDto.getUserId()));
        order.setUser(user.orElse(null));

        Optional<Prescription> prescription = prescriptionRepository.findById(UUID.fromString(prescriptionDto.getPrescriptionId()));

        if (prescription.isPresent() && prescription.get().getExpDate().isAfter(LocalDate.now())) {

            if (prescription.get().isActive()) {
                prescription.get().setActive(false);
                order.setPrescription(prescription.get());
            } else {
                throw new InActivePrescriptionExp(ErrorMessage.INACTIVE_PRESCRIPTION);
            }
        } else {
            throw new InActivePrescriptionExp(ErrorMessage.EXPIRED_PRESCRIPTION);
        }

        order.setOrderDate(LocalDate.now());

        BigDecimal orderCost = BigDecimal.ZERO;

        for (OrderDetail orderDetail : order.getOrderDetails()) {

            orderDetail.setOrder(order);

            Medicine medicine = medicineRepository.findMedicineByMedicineId(orderDetail.getMedicine().getMedicineId());

            int rest = medicine.getQuantity();
            int quantityInOrder = orderDetail.getQuantity();
            if (quantityInOrder < rest) {
                medicine.setQuantity(rest - quantityInOrder);
            } else {
                throw new NotEnoughBalanceExp(ErrorMessage
                        .NOT_ENOUGH_BALANCE + ", maximum quantity for " + medicine.getName() + " is " + rest);
            }
            orderDetail.setMedicine(medicine);
            orderDetail.setQuantity(orderDetail.getQuantity());
            orderCost = orderCost.add(BigDecimal.valueOf(orderDetail.getQuantity())
                    .multiply(orderDetail.getMedicine().getPrice()));
        }
        order.setOrderCost(orderCost);

        if (prescriptionDto.getDeliveryAddress() != null) {
            order.setDeliveryAddress(prescriptionDto.getDeliveryAddress());
        } else {
            OrderDetail orderDetail = order.getOrderDetails().stream().findFirst().orElse(null);
            assert orderDetail != null;
            order.setDeliveryAddress(orderDetail.getMedicine().getPharmacy().getAddress());
        }

        Order save = orderRepository.saveAndFlush(order);

        return orderFromPrescriptionMapper.toDto(save);
    }

    @Override
    public List<Order> showAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        if (orderList.isEmpty()) {
            throw new DataNotExistExp(ErrorMessage.N0_DATA_FOUND);
        }
        return orderList;
    }
}
