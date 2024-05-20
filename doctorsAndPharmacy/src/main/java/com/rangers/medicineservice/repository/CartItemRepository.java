package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> getAllByUser(User user);
}
