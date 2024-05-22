package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.exception.ObjectDoesNotExistException;
import com.rangers.medicineservice.exception.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.PrescriptionMapper;
import com.rangers.medicineservice.repository.PrescriptionRepository;
import com.rangers.medicineservice.repository.UserRepository;
import com.rangers.medicineservice.service.interf.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    PrescriptionMapper prescriptionMapper;
    @Autowired
    UserRepository userRepository;

    @Override
    public Prescription getPrescription(String prescriptionId) {
        return prescriptionRepository.findById(UUID.fromString(prescriptionId))
                .orElseThrow(() -> new ObjectDoesNotExistException(ErrorMessage.PRESCRIPTIONS_NOT_FOUND));
    }

    @Override
    public PrescriptionDto getPrescriptionDto(String prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(UUID.fromString(prescriptionId))
                .orElseThrow(() -> new ObjectDoesNotExistException(ErrorMessage.PRESCRIPTIONS_NOT_FOUND));
        return prescriptionMapper.toDto(prescription);
    }

    @Override
    public List<PrescriptionDto> getActivePrescriptions(String userId) {
//        List<Prescription> prescriptions = prescriptionRepository.findActive(UUID.fromString(userId));
        List<Prescription> prescriptionList = prescriptionRepository.findByUserId(UUID.fromString(userId));
        List<Prescription> prescriptions = prescriptionList.stream()
                .filter(Prescription::isActive)
                .toList();
        List<PrescriptionDto> prescriptionDtoList = new ArrayList<>();

        if (prescriptions.isEmpty()) {
            System.out.println("111111111111111111111");
            return Collections.emptyList();
        } else {
            for (Prescription prescription : prescriptions) {
                prescriptionDtoList.add(prescriptionMapper.toDto(prescription));
                System.out.println("!!!!!!!!!!!!!!!!!"+ prescriptionDtoList);
            }
        }
        return prescriptionDtoList;
    }
}
