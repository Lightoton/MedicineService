package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.annotation.GetAvailableMedicines;
import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.service.interf.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @GetAvailableMedicines(path = "/getAvailable")
    List<MedicineDto> getAvailable(){
        return medicineService.getAvailable();
    }
}
