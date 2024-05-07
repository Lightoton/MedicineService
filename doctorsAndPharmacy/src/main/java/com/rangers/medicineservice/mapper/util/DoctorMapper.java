package com.rangers.medicineservice.mapper.util;

import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.entity.Doctor;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DoctorMapper {

    @Mappings({
            @Mapping(target = "fullName", ignore = true),
            @Mapping(target = "rating", source = "rating"),
            @Mapping(target = "specialization", source = "specialization")
    })
    public DoctorDto toDto(Doctor doctor);

    @AfterMapping
    default void generateDoctorDto(@MappingTarget DoctorDto doctorDto, Doctor doctor){
        String fullName = doctor.getFirstName() + " " + doctor.getLastName();
        doctorDto.setFullName(fullName);
    }
}
