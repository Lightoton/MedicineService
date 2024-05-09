package com.rangers.medicineservice.exсeption;

public class ListIsEmptyException extends BadRequestException {
    public ListIsEmptyException(String message) {
        super(message);
    }
}
