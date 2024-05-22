package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.enums.MedicineCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, UUID> {

    Medicine findMedicineByMedicineId(UUID medicineId);

    Medicine findByName(String name);

    List<Medicine> findAllByAvailableQuantityGreaterThan(int quantity);

    @Transactional
    @Modifying
    @Query("UPDATE Medicine m SET m.availableQuantity = 0")
    void resetAvailableQuantity();

    List<Medicine> findByCategoryAndAvailableQuantityGreaterThan(MedicineCategory category, int quantity);

    Optional<Medicine> findByName(String name);
}