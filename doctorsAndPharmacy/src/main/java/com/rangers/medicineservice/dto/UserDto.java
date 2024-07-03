package com.rangers.medicineservice.dto;

import lombok.Data;

@Data
public class UserDto {
    String userId;
    String firstname;
    String lastname;
    String email;
    String phone;
    String address;
}
