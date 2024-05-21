package com.rangers.medicineservice.mapper.util;

import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.entity.Medicine;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicineMapper {
    @Named("toDto")
    MedicineDto toDto(Medicine medicine);

    @AfterMapping
    default void generateMedicineDto(@MappingTarget MedicineDto medicineDto, Medicine medicine) {
        medicineDto.setName(medicine.getName());
        medicineDto.setDescription(medicine.getDescription());
        medicineDto.setPrice(String.valueOf(medicine.getPrice()));
        medicineDto.setCategory(String.valueOf(medicine.getCategory()));
        medicineDto.setAvailableQuantity(String.valueOf(medicine.getAvailableQuantity()));
    }

    Medicine toEntity(MedicineDto medicineDto);
}
