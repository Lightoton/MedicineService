package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.exeption.ListIsEmptyException;
import com.rangers.medicineservice.exeption.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.util.MedicineMapper;
import com.rangers.medicineservice.repository.MedicineRepository;
import com.rangers.medicineservice.service.interf.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;

    @Transactional
    @Override
    public List<MedicineDto> getAvailable() {
        List<Medicine> medicineList =  medicineRepository.findAllByAvailableQuantityGreaterThan(0);
        if (medicineList.isEmpty()){
            throw new ListIsEmptyException(ErrorMessage.THERE_ARE_NO_MEDICINES);
        }
        return medicineList.stream().map(medicineMapper::toDto).toList();
    }

    @Transactional
    @Override
    public void resetQuantity() {
        medicineRepository.resetAvailableQuantity();
    }
}
