package com.rangers.medicineservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "recepts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recept_id")
    private UUID receptId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "exp_date")
    private LocalDate expDate;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "is_active")
    private boolean isActive;


    private Doctor doctor;

    private List<Medicine> madicine;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription prescription = (Prescription) o;
        return isActive == prescription.isActive && Objects.equals(receptId, prescription.receptId) && Objects.equals(quantity, prescription.quantity) && Objects.equals(expDate, prescription.expDate) && Objects.equals(createdAt, prescription.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receptId, quantity, expDate, createdAt, isActive);
    }

    @Override
    public String toString() {
        return "Recept{" +
                "receptId=" + receptId +
                ", quantity=" + quantity +
                ", expDate=" + expDate +
                ", createdAt=" + createdAt +
                ", isActive=" + isActive +
                ", doctor=" + doctor +
                ", madicine=" + madicine +
                ", user=" + user +
                '}';
    }
}
