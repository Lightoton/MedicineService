package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.DoctorDto;

import java.util.List;

public interface DoctorService {

    List<DoctorDto> getDoctors(String specialization);
}
