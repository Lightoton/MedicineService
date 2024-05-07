package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User getUserByChatId(String chatId);
    User getUserByPolicyNumber(String policyNumber);
}
