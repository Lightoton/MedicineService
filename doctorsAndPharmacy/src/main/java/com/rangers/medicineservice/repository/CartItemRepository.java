package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    CartItem findByCartItemId(UUID cartId);
}
