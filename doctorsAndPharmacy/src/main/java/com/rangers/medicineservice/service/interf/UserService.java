package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.PrescriptionDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<PrescriptionDto> getUserPrescriptions(UUID id);
}
