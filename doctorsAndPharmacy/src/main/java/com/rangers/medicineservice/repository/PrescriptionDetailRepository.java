package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.PrescriptionDetail;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, Prescription> {
    @NotNull
    List<PrescriptionDetail> findPrescriptionDetailsByPrescription(Prescription prescription);
}
