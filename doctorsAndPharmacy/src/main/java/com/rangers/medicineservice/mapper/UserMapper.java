package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.UserDto;
import com.rangers.medicineservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
