package com.rangers.medicineservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rangers.medicineservice.entity.enums.MedicineCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "medicines")
@NoArgsConstructor
@Getter
@Setter
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "medicine_id")
    private UUID medicineId;

    @Column(name = "medicine_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private MedicineCategory category;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pharmacy_id", referencedColumnName = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToMany(mappedBy = "medicines", fetch = FetchType.EAGER)
    Set<Prescription> prescriptions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return Objects.equals(medicineId, medicine.medicineId) && Objects.equals(name, medicine.name) && Objects.equals(description, medicine.description) && Objects.equals(price, medicine.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineId, name, description, price, category);
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "doctorId=" + medicineId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
