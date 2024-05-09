package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.UserAfterRegistrationDto;
import com.rangers.medicineservice.dto.UserDto;
import com.rangers.medicineservice.dto.UserInfoDto;
import com.rangers.medicineservice.dto.UserRegistrationDto;
import com.rangers.medicineservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserInfoDto toDto(User user);
    User toEntityFromUserRegistrationDto(UserRegistrationDto userRegistrationDto);
    UserAfterRegistrationDto toAfterRegistrationDto(User user);
    User toEntity(UserDto userDto);
}
