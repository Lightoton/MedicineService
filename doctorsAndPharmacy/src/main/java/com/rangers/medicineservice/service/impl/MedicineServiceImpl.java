package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.enums.MedicineCategory;
import com.rangers.medicineservice.exception.ListIsEmptyException;
import com.rangers.medicineservice.exception.ObjectDoesNotExistException;
import com.rangers.medicineservice.exception.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.util.MedicineMapper;
import com.rangers.medicineservice.repository.MedicineRepository;
import com.rangers.medicineservice.service.interf.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<MedicineDto> getByCategory(String category) {
        List<Medicine> medicineList = medicineRepository
                .findByCategoryAndAvailableQuantityGreaterThan(MedicineCategory.valueOf(category), 0);
        if (medicineList.isEmpty()){
            throw new ListIsEmptyException(ErrorMessage.THERE_ARE_NO_MEDICINES);
        }
        return medicineList.stream().map(medicineMapper::toDto).toList();
    }

    @Override
    public MedicineDto getByName(String medicineName) {
        Medicine medicine = medicineRepository.findByName(medicineName);
        if (medicine == null){
            throw new ObjectDoesNotExistException(ErrorMessage.THERE_ARE_NO_MEDICINES);
        }
        return medicineMapper.toDto(medicine);
    }
}
