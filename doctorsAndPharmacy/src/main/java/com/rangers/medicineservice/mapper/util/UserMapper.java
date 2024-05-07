package com.rangers.medicineservice.mapper.util;

import com.rangers.medicineservice.dto.UserDto;
import com.rangers.medicineservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
