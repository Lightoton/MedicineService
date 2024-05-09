package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.entity.Medicine;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface MedicineMapper {
    @Named("toDto")
    MedicineDto toDto(Medicine medicine);

    Medicine toEntity(MedicineDto medicineDto);
}
