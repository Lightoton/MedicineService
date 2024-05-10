package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
    @Query("SELECT p FROM Prescription p WHERE p.user.userId = :userId")
    List<Prescription> findByUserId(UUID userId);
}
