package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.dto.UserHistoryOrdersDto;
import com.rangers.medicineservice.entity.Order;
import com.rangers.medicineservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @NotNull
    Order saveAndFlush(@NotNull Order order);

    @Query("select o from Order o join o.user u where u.userId = :userId")
    List<Order> getOrdersByUserId(UUID userId);

}
