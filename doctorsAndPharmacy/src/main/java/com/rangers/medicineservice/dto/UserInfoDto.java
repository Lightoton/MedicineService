package com.rangers.medicineservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserInfoDto {
    private UUID userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String policyNumber;
}
