package com.rangers.medicineservice.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class UserAfterRegistrationDto {
    String operation = "USER CREATION";
    String status = "CREATED";
    String firstname;
    String lastname;
    String email;
    String phoneNumber;
    String address;
    String city;
    String country;
    String postalCode;
    String policyNumber;
}
