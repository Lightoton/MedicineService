package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @NotNull
    Order saveAndFlush(@NotNull Order order);
}
