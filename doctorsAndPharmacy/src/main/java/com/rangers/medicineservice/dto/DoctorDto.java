package com.rangers.medicineservice.dto;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class DoctorDto {
    UUID uuid;
    String fullName;
    String rating;
    String specialization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorDto doctorDto = (DoctorDto) o;
        return Objects.equals(fullName, doctorDto.fullName) && Objects.equals(specialization, doctorDto.specialization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, specialization);
    }
}
