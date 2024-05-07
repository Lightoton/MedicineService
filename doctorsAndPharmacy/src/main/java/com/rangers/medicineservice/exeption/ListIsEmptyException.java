package com.rangers.medicineservice.exeption;

public class ListIsEmptyException extends BadRequestException {
    public ListIsEmptyException(String message) {
        super(message);
    }
}
