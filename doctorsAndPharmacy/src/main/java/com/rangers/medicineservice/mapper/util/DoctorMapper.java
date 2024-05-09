package com.rangers.medicineservice.mapper.util;

import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.entity.Doctor;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorMapper {

//    @Mappings({
//            @Mapping(target = "fullName", ignore = true),
//            @Mapping(target = "rating", ignore = true),
//            @Mapping(target = "specialization", ignore = true)
//    })
    public DoctorDto toDto(Doctor doctor);

    @AfterMapping
    default void generateDoctorDto(@MappingTarget DoctorDto doctorDto, Doctor doctor){
        doctorDto.setRating(doctor.getRating());
        doctorDto.setSpecialization(String.valueOf(doctor.getSpecialization()));
        String fullName = doctor.getFirstName() + " " + doctor.getLastName();
        doctorDto.setFullName(fullName);
    }
}
