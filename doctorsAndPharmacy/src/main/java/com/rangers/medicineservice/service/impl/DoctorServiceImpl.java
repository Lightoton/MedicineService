package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.entity.Doctor;
import com.rangers.medicineservice.entity.enums.Specialization;
import com.rangers.medicineservice.exсeption.ListIsEmptyException;
import com.rangers.medicineservice.exсeption.SpecializationDoesNotExistException;
import com.rangers.medicineservice.exсeption.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.util.DoctorMapper;
import com.rangers.medicineservice.repository.DoctorRepository;
import com.rangers.medicineservice.service.interf.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    @Override
    public List<DoctorDto> getDoctors(String specialization) {

        try {
            Specialization.valueOf(specialization);
        } catch (IllegalArgumentException e) {
            throw new SpecializationDoesNotExistException(ErrorMessage.SPECIALIZATION_DOES_NOT_EXIST);
        }
        List<Doctor> doctorList = doctorRepository.findAllBySpecialization(Specialization.valueOf(specialization));
        List<DoctorDto> doctorDtoList = doctorList.stream()
                .map(doctorMapper::toDto)
                .toList();
        if (doctorDtoList.isEmpty()) throw new ListIsEmptyException(ErrorMessage
                                                            .THERE_ARE_NO_DOCTORS_IN_THIS_SPECIALIZATION);
        return doctorDtoList;
    }
}
