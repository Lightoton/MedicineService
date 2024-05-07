package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, UUID> {
    List<Medicine> findAllByAvailableQuantityGreaterThan(int quantity);

    @Transactional
    @Modifying
    @Query("UPDATE Medicine m SET m.availableQuantity = 0")
    void resetAvailableQuantity();
}
