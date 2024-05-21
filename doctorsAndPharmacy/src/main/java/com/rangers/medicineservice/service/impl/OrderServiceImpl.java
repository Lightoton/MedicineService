package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.entity.*;
import com.rangers.medicineservice.exception.DataNotExistExp;
import com.rangers.medicineservice.exception.InActivePrescriptionExp;
import com.rangers.medicineservice.exception.NotEnoughBalanceExp;
import com.rangers.medicineservice.exception.RunOutOfMedicineException;
import com.rangers.medicineservice.exception.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.*;
import com.rangers.medicineservice.mapper.util.MedicineMapper;
import com.rangers.medicineservice.repository.*;
import com.rangers.medicineservice.service.interf.OrderService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartItemRepository cartItemRepository;
    private final PrescriptionDetailRepository prescriptionDetailRepository;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final UserMapper userMapper;
    private final MedicineMapper medicineMapper;
    private final OrderMapper orderMapper;
    private final OrderFromPrescriptionMapper orderFromPrescriptionMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public CreatedOrderDto createOrder(Set<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart items set cannot be empty.");
        }

        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal orderCost = BigDecimal.ZERO;
        OrderBeforeCreation orderBeforeCreation = new OrderBeforeCreation();

        for (CartItem cartItem : cartItems) {
            CartItemToOrderDetailDto dto = cartItemMapper.toOrderDetailDto(
                    cartItemRepository.findById(cartItem.getCartItemId()).orElse(null)
            );

            Medicine medicine = medicineRepository.findByName(dto.getMedicine().getName());
            if (medicine == null) {
                throw new IllegalArgumentException("Medicine not found: " + dto.getMedicine().getName());
            }

            if (dto.getQuantity() < medicine.getAvailableQuantity()) {
                OrderDetail orderDetail = orderMapper.toOrderDetail(dto);
                orderDetail.setMedicine(medicine);
                orderDetails.add(orderDetail);
                BigDecimal itemCost = medicine.getPrice().multiply(new BigDecimal(orderDetail.getQuantity()));
                orderCost = orderCost.add(itemCost);

                // Update availableQuantity in medicine
                int newQuantity = medicine.getAvailableQuantity() - dto.getQuantity();
                medicine.setAvailableQuantity(newQuantity);
                medicineRepository.save(medicine);  // Сохраните обновленный объект Medicine
            } else {
                throw new RunOutOfMedicineException(ErrorMessage.RUN_OUT_OF_MEDICINE);
            }
        }

        orderBeforeCreation.setOrderDetails(orderDetails);
        orderBeforeCreation.setOrderCost(orderCost);

        Order order = orderMapper.toEntity(orderMapper.toDto(orderBeforeCreation));
        order.setOrderCost(orderCost);
        order.setOrderDetails(orderDetails);

        // Определяем пользователя из первого элемента корзины
        CartItem firstCartItem = cartItems.iterator().next();
        CartItemToOrderDetailDto firstDto = cartItemMapper.toOrderDetailDto(
                cartItemRepository.findById(firstCartItem.getCartItemId()).orElse(null)
        );
        order.setUser(userMapper.toEntity(firstDto.getUser()));

        // Сохраняем все детали заказа
        for (OrderDetail detail : orderDetails) {
            detail.setOrder(order);
            orderDetailRepository.save(detail);
        }

        orderRepository.saveAndFlush(order);  // Сохраняем заказ

        return orderMapper.toDto(orderBeforeCreation);
    }

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

