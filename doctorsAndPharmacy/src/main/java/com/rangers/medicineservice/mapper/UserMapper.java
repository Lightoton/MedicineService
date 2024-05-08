package com.rangers.medicineservice.mapper;

import java.sql.Timestamp;

import com.rangers.medicineservice.dto.UserAfterRegistrationDto;
import com.rangers.medicineservice.dto.UserInfoDto;
import com.rangers.medicineservice.dto.UserRegistrationDto;
import com.rangers.medicineservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface UserMapper {
    UserInfoDto toDto(User user);
    User toEntityFromUserRegistrationDto(UserRegistrationDto userRegistrationDto);
    UserAfterRegistrationDto toAfterRegistrationDto(User user);
}
