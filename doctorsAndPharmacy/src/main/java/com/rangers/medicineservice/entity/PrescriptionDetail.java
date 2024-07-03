package com.rangers.medicineservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "prescription_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "prescription_details_id")
    private UUID prescriptionDetailsId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "prescription_id", referencedColumnName = "prescription_id")
    private Prescription prescription;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "medicine_id", referencedColumnName = "medicine_id")
    private Medicine medicine;


    @Column(name = "quantity")
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescriptionDetail that = (PrescriptionDetail) o;
        return quantity == that.quantity && Objects.equals(prescriptionDetailsId, that.prescriptionDetailsId) && Objects.equals(medicine, that.medicine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionDetailsId, medicine, quantity);
    }


    @Override
    public String toString() {
        return "\n" + medicine.getName() + ", quantity=" + quantity;
    }
}