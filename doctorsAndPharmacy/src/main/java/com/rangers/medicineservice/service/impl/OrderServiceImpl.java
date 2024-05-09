package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.CartItemToOrderDetailDto;
import com.rangers.medicineservice.dto.CreatedOrderDto;
import com.rangers.medicineservice.dto.OrderBeforeCreation;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.*;
import com.rangers.medicineservice.mapper.CartItemMapper;
import com.rangers.medicineservice.mapper.MedicineMapper;
import com.rangers.medicineservice.mapper.OrderMapper;
import com.rangers.medicineservice.mapper.UserMapper;
import com.rangers.medicineservice.repository.CartItemRepository;
import com.rangers.medicineservice.repository.OrderDetailRepository;
import com.rangers.medicineservice.repository.OrderRepository;
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
        OrderBeforeCreation orderBeforeCreation = new OrderBeforeCreation(); // Создаем один объект для заказа
        for (CartItem cartItem : cartItems) {
            CartItemToOrderDetailDto dto = cartItemMapper.toOrderDetailDto(
                    cartItemRepository.findById(cartItem.getCartItemId()).orElse(null)
            );
            OrderDetail orderDetail = orderMapper.toOrderDetail(dto);
            orderDetail.setMedicine(medicineMapper.toEntity(dto.getMedicine()));
            orderDetails.add(orderDetail);
            BigDecimal itemCost = orderDetail.getMedicine().getPrice().multiply(new BigDecimal(orderDetail.getQuantity()));
            orderCost = orderCost.add(itemCost);
        }
        orderBeforeCreation.setOrderDetails(orderDetails); // Устанавливаем все детали заказа
        orderBeforeCreation.setOrderCost(orderCost); // Устанавливаем стоимость заказа

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
        orderRepository.saveAndFlush(order); // Сохраняем заказ
        return orderMapper.toDto(orderBeforeCreation);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
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

