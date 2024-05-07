package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.annotation.GetDoctors;
import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.service.interf.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetDoctors(path = "/getDoctors/{specialization}")
    List<DoctorDto> getDoctors(@PathVariable(name = "specialization") String specialization){
        return doctorService.getDoctors(specialization);
    }
}
