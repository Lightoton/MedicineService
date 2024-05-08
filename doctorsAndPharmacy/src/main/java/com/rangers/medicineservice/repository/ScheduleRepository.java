package com.rangers.medicineservice.repository;

import com.rangers.medicineservice.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.rangers.medicineservice.entity.Doctor;
import com.rangers.medicineservice.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    Schedule findByScheduleId(UUID id);

    @Query("SELECT s FROM Schedule s WHERE s.doctor.doctorId = :doctorId AND s.status = 'FREE'")
    List<Schedule> findByDoctor(@Param("doctorId") UUID doctorId);

    @Query("SELECT s FROM Schedule s WHERE s.doctor.doctorId = :doctorId AND DATE(s.dateTime) = :date")
    List<Schedule> findByDoctorIdAndDate(@Param("doctorId") UUID doctorId, @Param("date") LocalDate date);

    @Query("SELECT s FROM Schedule s WHERE s.doctor.doctorId = :doctorId AND s.dateTime = :time")
    Schedule findByDoctorIdAndDateAndTime(@Param("doctorId") UUID doctorId,@Param("time") LocalDateTime time);

}
