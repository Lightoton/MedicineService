package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.CartItemToOrderDetailDto;
import com.rangers.medicineservice.dto.CreatedOrderDto;
import com.rangers.medicineservice.dto.OrderBeforeCreation;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.Order;
import com.rangers.medicineservice.entity.OrderDetail;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.mapper.util.CartItemMapper;
import com.rangers.medicineservice.mapper.util.OrderMapper;
import com.rangers.medicineservice.repository.CartItemRepository;
import com.rangers.medicineservice.repository.OrderDetailRepository;
import com.rangers.medicineservice.repository.OrderRepository;
import com.rangers.medicineservice.service.OrderService;
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
    private  final OrderDetailRepository orderDetailRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public CreatedOrderDto createOrder(CartItem cartItem) {
        cartItem = cartItemRepository.findByCartItemId(cartItem.getCartItemId());
        CartItemToOrderDetailDto cartItemToOrderDetailDto = cartItemMapper.toOrderDetailDto(cartItem);//CartItem to OrderDetail
        OrderDetail orderDetail = orderMapper.toOrderDetail(cartItemToOrderDetailDto);
        orderDetail.setMedicine(cartItem.getMedicine()); //set medicine to OrderDetail

        OrderBeforeCreation orderBeforeCreation = orderMapper.toOrderBeforeCreation(orderDetail); // OrderDetail to Order
        orderBeforeCreation.setUser(cartItemToOrderDetailDto.getUser()); //set User to Order

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        orderBeforeCreation.setOrderDetails(orderDetailList);//set OrderDetail to Order

        BigDecimal cost = BigDecimal.valueOf(0);

        for (OrderDetail detail : orderBeforeCreation.getOrderDetails()) {
            orderBeforeCreation.setOrderCost(cost.add(detail.getMedicine().getPrice())); //set orderCost
        }

        orderDetail.setOrder(orderMapper.toEntity(orderMapper.toDto(orderBeforeCreation)));//set Order to OrderDetail

        orderDetailRepository.saveAndFlush(orderDetail);//save OrderDetail
        orderRepository.saveAndFlush(orderMapper.toEntity(orderMapper.toDto(orderBeforeCreation)));//save Order

        User user = cartItem.getUser();
        Set<Order> orders= new HashSet<>();
        orders.add(orderMapper.toEntity(orderMapper.toDto(orderBeforeCreation)));
        user.getOrders().clear();
        user.getOrders().addAll(orders); //set Order to User

        return orderMapper.toDto(orderBeforeCreation);
    }
}
