package com.rangers.medicineservice.controller.handler;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ErrorExtension {
    String message;
    HttpStatus errorCode;
    int statusCode;
    public ErrorExtension(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
