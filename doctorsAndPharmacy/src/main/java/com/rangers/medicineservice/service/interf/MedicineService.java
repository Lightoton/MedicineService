package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.entity.Medicine;

import java.util.List;

public interface MedicineService {
    List<MedicineDto> getAvailable();

    void resetQuantity();
}
