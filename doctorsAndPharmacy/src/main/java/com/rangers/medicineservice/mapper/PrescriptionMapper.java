package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Doctor;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.User;
import org.mapstruct.*;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrescriptionMapper {

    @Mapping(target = "prescriptionId", source = "prescriptionId")
    @Mapping(target = "expiryDate", expression = "java(prescription.getExpDate())")
    @Mapping(target = "userId", source = "user", qualifiedByName = "getUserId")
    PrescriptionDto toDto(Prescription prescription);

    Prescription toEntity(PrescriptionDto prescriptionDto);


    @Named("getUserId")
    default String getUserId(User user) {
        if (user != null) {
            return user.getUserId().toString();
        }
        return null;
    }
}
