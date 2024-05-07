package com.rangers.medicineservice.mapper;

import java.sql.Timestamp;

import com.rangers.medicineservice.dto.UserAfterRegistrationDto;
import com.rangers.medicineservice.dto.UserInfoDto;
import com.rangers.medicineservice.dto.UserRegistrationDto;
import com.rangers.medicineservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", imports = Timestamp.class)
public interface UserMapper {
    UserInfoDto toDto(User user);
    User toEntityFromUserRegistrationDto(UserRegistrationDto userRegistrationDto);
    UserAfterRegistrationDto toAfterRegistrationDto(User user);
}
