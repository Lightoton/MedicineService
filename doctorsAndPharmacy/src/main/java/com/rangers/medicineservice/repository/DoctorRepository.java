package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.Doctor;
import com.rangers.medicineservice.entity.enums.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findAllBySpecialization(Specialization specialization);
}
