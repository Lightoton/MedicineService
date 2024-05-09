package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.Prescription;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
    @NotNull
    Optional<Prescription> findById(@NotNull UUID uuid);
}
