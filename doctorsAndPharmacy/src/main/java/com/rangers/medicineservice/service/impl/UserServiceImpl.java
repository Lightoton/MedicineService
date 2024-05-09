package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.mapper.PrescriptionMapper;
import com.rangers.medicineservice.repository.UserRepository;
import com.rangers.medicineservice.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    public List<PrescriptionDto> getUserPrescriptions(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        List<PrescriptionDto> prescriptionDtoList = new ArrayList<>();

        if (user == null || user.getPrescriptions().isEmpty()) {
            return Collections.emptyList();
        } else {
            for (Prescription prescription : user.getPrescriptions()) {
                prescriptionDtoList.add(prescriptionMapper.toDto(prescription));
            }
        }
        return prescriptionDtoList;
    }
}
