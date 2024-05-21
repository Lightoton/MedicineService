package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.User;
import org.jetbrains.annotations.NotNull;
import com.rangers.medicineservice.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {

    @Query("SELECT p FROM Prescription p WHERE p.user.userId = :userId")
    List<Prescription> findByUserId(UUID userId);

    @NotNull
    Optional<Prescription> findById(@NotNull UUID uuid);
    @Query("SELECT p FROM Prescription p WHERE p.user.userId = :userId AND p.isActive = true")
    List<Prescription> findActive(UUID userId);
}
