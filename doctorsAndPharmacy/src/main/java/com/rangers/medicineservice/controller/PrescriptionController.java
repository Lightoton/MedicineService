package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.annotation.GetActivePrescriptions;
import com.rangers.medicineservice.annotation.GetDoctors;
import com.rangers.medicineservice.annotation.GetPrescription;
import com.rangers.medicineservice.annotation.GetPrescriptionDto;
import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.service.interf.DoctorService;
import com.rangers.medicineservice.service.interf.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetPrescription(path = "/getPrescription/{prescription_id}")
    Prescription getPrescription(@PathVariable(name = "prescription_id") String prescription_id){
        return prescriptionService.getPrescription(prescription_id);
    }

    @GetPrescriptionDto(path = "/getPrescriptionDto/{prescription_id}")
    PrescriptionDto getPrescriptionDto(@PathVariable(name = "prescription_id") String prescription_id){
        return prescriptionService.getPrescriptionDto(prescription_id);
    }

    @GetActivePrescriptions(path = "/getActive/{user_id}")
    List<PrescriptionDto> getActive(@PathVariable(name = "user_id") String user_id){
        return prescriptionService.getActivePrescriptions(user_id);
    }
}
