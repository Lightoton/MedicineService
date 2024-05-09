package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User getUserByChatId(String chatId);
    User getUserByPolicyNumber(String policyNumber);
    @NotNull
    Optional<User> findById(@NotNull UUID uuid);
}
