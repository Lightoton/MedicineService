package com.rangers.medicineservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "pharmacies")
@NoArgsConstructor
@Getter
@Setter
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pharmacy_id")
    private UUID pharmacyId;

    @Column(name = "pharmacy_name")
    private String pharmacyName;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @OneToMany(mappedBy = "pharmacy", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Medicine> medicines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return Objects.equals(pharmacyId, pharmacy.pharmacyId) && Objects.equals(pharmacyName, pharmacy.pharmacyName)
                && Objects.equals(address, pharmacy.address) && Objects.equals(city, pharmacy.city)
                && Objects.equals(country, pharmacy.country) && Objects.equals(postalCode, pharmacy.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pharmacyId, pharmacyName, address, city, country, postalCode);
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "pharmacyId=" + pharmacyId +
                ", pharmacyName='" + pharmacyName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
