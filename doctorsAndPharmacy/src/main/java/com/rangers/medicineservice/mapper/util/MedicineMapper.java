package com.rangers.medicineservice.mapper.util;

import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.entity.Medicine;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface MedicineMapper {
    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "category", source = "category")
    })
    public MedicineDto toDto(Medicine medicine);
}
