package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.UserHistoryPrescriptionDetailsDto;
import com.rangers.medicineservice.dto.UserHistoryPrescriptionsDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Doctor;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.PrescriptionDetail;
import com.rangers.medicineservice.entity.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface PrescriptionMapper {
    List<UserHistoryPrescriptionsDto> toUserHistoryPrescriptionsDtoList(List<Prescription> prescriptionList);

    @Mappings({
            @Mapping(target = "medicineName", source = "prescriptionDetail.medicine.name"),
            @Mapping(target = "quantity", source = "prescriptionDetail.quantity"),
            @Mapping(target = "medicinePrice", source = "prescriptionDetail.medicine.price")
    })
    UserHistoryPrescriptionDetailsDto mapToUserHistoryPrescriptionsDto(PrescriptionDetail prescriptionDetail);

    @AfterMapping
    default void updatePrescriptionsDto(Prescription prescription, @MappingTarget UserHistoryPrescriptionsDto userHistoryPrescriptionsDto) {
        userHistoryPrescriptionsDto.setDoctorName(prescription.getDoctor().getFirstName()+" "+prescription.getDoctor().getLastName());

        List<UserHistoryPrescriptionDetailsDto> userHistoryPrescriptionDetailsDtoList = prescription.getPrescriptionDetails().stream()
                .map(this::mapToUserHistoryPrescriptionsDto)
                .toList();
        userHistoryPrescriptionsDto.setUserHistoryPrescriptionDetailsDtoList(userHistoryPrescriptionDetailsDtoList);

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
