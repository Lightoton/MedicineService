package com.rangers.medicineservice.mapper.util;

import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Doctor;
import com.rangers.medicineservice.entity.Prescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    @Mapping(target = "prescriptionId", source = "prescriptionId")
    @Mapping(target = "expDate", expression = "java(String.valueOf(prescription.getExpDate()))")
    @Mapping(target = "createdAt", expression = "java(String.valueOf(prescription.getCreatedAt()))")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "doctorName", source = "doctor", qualifiedByName = "fullName")
    PrescriptionDto toDto(Prescription prescription);

    Prescription toEntity(PrescriptionDto prescriptionDto);


    @Named("fullName")
    default String getDoctorFullName(Doctor doctor) {
        if (doctor != null) {
            return doctor.getFirstName() + " " + doctor.getLastName();
        }
        return null; // Или можно вернуть пустую строку, если это предпочтительнее
    }
}
