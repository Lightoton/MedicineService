package com.rangers.medicineservice.exception;

public class ListIsEmptyException extends BadRequestException {
    public ListIsEmptyException(String message) {
        super(message);
    }
}
