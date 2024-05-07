package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.Doctor;
import com.rangers.medicineservice.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    Schedule findByScheduleId(UUID id);
}
