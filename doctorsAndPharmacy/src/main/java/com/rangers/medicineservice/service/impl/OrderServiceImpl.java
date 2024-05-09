package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.OrderFromPrescriptionDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.*;
import com.rangers.medicineservice.exception.DataNotExistExp;
import com.rangers.medicineservice.exception.InActivePrescriptionExp;
import com.rangers.medicineservice.exception.NotEnoughBalanceExp;
import com.rangers.medicineservice.exception.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.OrderFromPrescriptionMapper;
import com.rangers.medicineservice.repository.*;
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
    private final PrescriptionDetailRepository prescriptionDetailRepository;

    @Override
    @Transactional
    public OrderFromPrescriptionDto addOrder(PrescriptionDto prescriptionDto) {

        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(UUID.fromString(prescriptionDto.getPrescriptionId()));
        List<PrescriptionDetail> details = prescriptionDetailRepository
                .findPrescriptionDetailsByPrescription(prescription);
        if (details.isEmpty()) {
            throw new DataNotExistExp(ErrorMessage.N0_DATA_FOUND);
        }
        if (prescriptionDto.getUserId().equals(details.getFirst().getPrescription().getUser().getUserId().toString())) {

            Order order = orderFromPrescriptionMapper.toEntity(details, prescriptionDto);


            Optional<User> user = userRepository.findById(UUID.fromString(prescriptionDto.getUserId()));
            order.setUser(user.orElse(null));

            Optional<Prescription> newPrescription = prescriptionRepository
                    .findById(UUID.fromString(prescriptionDto.getPrescriptionId()));

            if (newPrescription.isPresent() && newPrescription.get().getExpDate().isAfter(LocalDate.now())) {

                if (newPrescription.get().isActive()) {
                    newPrescription.get().setActive(false);
                    order.setPrescription(newPrescription.get());
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

                Medicine medicine = medicineRepository
                        .findMedicineByMedicineId(orderDetail.getMedicine().getMedicineId());

                int rest = medicine.getAvailableQuantity();
                int quantityInOrder = orderDetail.getQuantity();

                if (quantityInOrder < rest) {
                    medicine.setAvailableQuantity(rest - quantityInOrder);
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

            if (!prescriptionDto.getDeliveryAddress().isEmpty()) {
                order.setDeliveryAddress(prescriptionDto.getDeliveryAddress());
            } else {

                OrderDetail orderDetail = order.getOrderDetails().stream().findFirst().orElse(null);
                assert orderDetail != null;

                order.setPharmacy(orderDetail.getMedicine().getPharmacy());
                order.setDeliveryAddress(orderDetail.getMedicine().getPharmacy().getAddress());
            }

            Order save = orderRepository.saveAndFlush(order);

            return orderFromPrescriptionMapper.toDto(save);


        } else {
            throw new DataNotExistExp(ErrorMessage.WRONG_PRESCRIPTION);
        }

    }

    @Override
    public List<Order> showAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        if (orderList.isEmpty()) {
            throw new DataNotExistExp(ErrorMessage.ORDERS_IS_EMPTY);
        }
        return orderList;
    }
}
