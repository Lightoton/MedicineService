package com.rangers.medicineservice.entity;

import com.rangers.medicineservice.entity.enums.AppointmentType;
import com.rangers.medicineservice.entity.enums.OrderStatus;
import com.rangers.medicineservice.entity.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "schedule_id")
    private UUID scheduleId;

    @Column(name = "date_and_time")
    private LocalDateTime dateAndTime;

    @Column(name = "status")
    private ScheduleStatus status;

    @Column(name = "type")
    private AppointmentType type;

    @Column(name = "link")
    private String link;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    private Doctor doctor;
}
