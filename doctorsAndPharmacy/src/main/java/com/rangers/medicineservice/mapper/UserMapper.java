package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.UserAfterRegistrationDto;
import com.rangers.medicineservice.dto.UserInfoDto;
import com.rangers.medicineservice.dto.UserRegistrationDto;
import com.rangers.medicineservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface UserMapper {
    UserInfoDto toDto(User user);
    User toEntityFromUserRegistrationDto(UserRegistrationDto userRegistrationDto);
    UserAfterRegistrationDto toAfterRegistrationDto(User user);
}
