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
import com.rangers.medicineservice.repository.UserRepository;
import com.rangers.medicineservice.service.interf.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
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
    public CreatedOrderDto createOrder(CartItem cartItem) {
        CartItemToOrderDetailDto cartItemToOrderDetailDto =
                cartItemMapper.toOrderDetailDto(
                        cartItemRepository.findById(cartItem.getCartItemId()).orElse(null)
                );

        OrderDetail orderDetail = orderMapper.toOrderDetail(cartItemToOrderDetailDto);
        orderDetail.setMedicine(medicineMapper.toEntity(cartItemToOrderDetailDto.getMedicine()));

        OrderBeforeCreation orderBeforeCreation = orderMapper.toOrderBeforeCreation(orderDetail);

        Order order = orderMapper.toEntity(orderMapper.toDto(orderBeforeCreation));

        //set OrderDetail to Order
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        orderBeforeCreation.setOrderDetails(orderDetailList);
        orderDetail.setOrder(order);

        //count total sum of order (orderCost)
        BigDecimal orderCost =
                orderBeforeCreation.getOrderDetails().stream()
                        .map(detail -> detail.getMedicine()
                                .getPrice()
                                .multiply(new BigDecimal(detail.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        //save orderCost
        orderBeforeCreation.setOrderCost(orderCost);
        order.setOrderCost(orderCost);

        order.setUser(userMapper.toEntity(cartItemToOrderDetailDto.getUser()));

        //set order to user
        Set<Order> orders = new HashSet<>();
        orders.add(order);
        userMapper.toEntity(cartItemToOrderDetailDto.getUser()).setOrders(orders);

        //saving
        orderDetailRepository.save(orderDetail);
        orderRepository.saveAndFlush(order);

        return orderMapper.toDto(orderBeforeCreation);
    }
}
