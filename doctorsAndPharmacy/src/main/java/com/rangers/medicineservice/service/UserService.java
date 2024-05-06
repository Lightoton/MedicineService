package com.rangers.medicineservice.service;

import com.rangers.medicineservice.entity.Prescription;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<Prescription> getUserPrescriptions(UUID id);
}
