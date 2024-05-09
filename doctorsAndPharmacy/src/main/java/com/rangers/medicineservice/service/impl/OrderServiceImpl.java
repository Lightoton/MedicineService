package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.CartItemToOrderDetailDto;
import com.rangers.medicineservice.dto.CreatedOrderDto;
import com.rangers.medicineservice.dto.OrderBeforeCreation;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.Order;
import com.rangers.medicineservice.entity.OrderDetail;
import com.rangers.medicineservice.mapper.util.CartItemMapper;
import com.rangers.medicineservice.mapper.util.MedicineMapper;
import com.rangers.medicineservice.mapper.util.OrderMapper;
import com.rangers.medicineservice.mapper.util.UserMapper;
import com.rangers.medicineservice.repository.CartItemRepository;
import com.rangers.medicineservice.repository.OrderDetailRepository;
import com.rangers.medicineservice.repository.OrderRepository;
import com.rangers.medicineservice.service.interf.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartItemRepository cartItemRepository;
    private final UserMapper userMapper;
    private final MedicineMapper medicineMapper;
    private final OrderMapper orderMapper;
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

}
