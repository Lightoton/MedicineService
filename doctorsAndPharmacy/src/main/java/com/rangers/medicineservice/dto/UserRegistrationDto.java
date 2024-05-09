package com.rangers.medicineservice.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String policyNumber;
    private String chatId;
}
