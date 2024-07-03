package com.rangers.medicineservice.dto;

import lombok.Value;

@Value
public class ErrorExtension {
    String message;
    String errorCode;
}
