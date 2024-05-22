package com.rangers.medicineservice.exception;

public class RunOutOfMedicineException extends RuntimeException{
    public RunOutOfMedicineException(String message) {
        super(message);
    }

}
