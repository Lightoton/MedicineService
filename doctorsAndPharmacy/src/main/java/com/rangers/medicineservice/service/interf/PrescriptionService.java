package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Prescription;

import java.util.List;

public interface PrescriptionService{
    Prescription getPrescription(String prescriptionId);
    PrescriptionDto getPrescriptionDto(String prescriptionId);
    List<PrescriptionDto> getActivePrescriptions(String userId);
}
