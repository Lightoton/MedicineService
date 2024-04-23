package com.rangers.medicineservice.entity;

import com.rangers.medicineservice.entity.enums.MedicineCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "medicine")
@NoArgsConstructor
@Getter
@Setter
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "medicine_id")
    private UUID doctorId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category")
    private MedicineCategory category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return Objects.equals(doctorId, medicine.doctorId) && Objects.equals(name, medicine.name) && Objects.equals(description, medicine.description) && Objects.equals(price, medicine.price) && category == medicine.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, name, description, price, category);
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "doctorId=" + doctorId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
