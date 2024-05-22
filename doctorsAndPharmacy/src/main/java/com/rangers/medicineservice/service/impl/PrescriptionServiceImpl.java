package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.*;
import com.rangers.medicineservice.exception.ObjectDoesNotExistException;
import com.rangers.medicineservice.exception.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.PrescriptionMapper;
import com.rangers.medicineservice.repository.*;
import com.rangers.medicineservice.service.interf.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    PrescriptionMapper prescriptionMapper;
    @Autowired
    UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final MedicineRepository medicineRepository;
    private final PrescriptionDetailRepository prescriptionDetailRepository;

    public void creatTestPrescription(UUID userId, UUID doctorId) {

        Prescription newPrescription = new Prescription();

        Optional<User> user = userRepository.findById(userId);
        newPrescription.setUser(user.orElse(null));

        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        newPrescription.setDoctor(doctor.orElse(null));

        newPrescription.setActive(true);

        LocalDate currentDate = LocalDate.now();
        newPrescription.setCreatedAt(currentDate);
        newPrescription.setExpDate(currentDate.plusMonths(1));
        Random random = new Random();

        List<PrescriptionDetail> ld = new ArrayList<>();
        List<Medicine> listM = medicineRepository.findAll();

        for (int i = 0; i < random.nextInt(1,5); i++) {
            PrescriptionDetail pd = new PrescriptionDetail();
            Medicine medicine = listM.get(random.nextInt(0, listM.size()));

            boolean isPresent = ld.stream().anyMatch(e -> e.getMedicine().getMedicineId() == medicine.getMedicineId());
            if (!isPresent) {
                pd.setMedicine(medicine);
                pd.setQuantity(1);
                pd.setPrescription(newPrescription);
                prescriptionDetailRepository.save(pd);
                ld.add(pd);
            }
        }
        newPrescription.setPrescriptionDetails(ld);

        prescriptionRepository.save(newPrescription);

    }

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
        List<Prescription> prescriptionList = prescriptionRepository.findByUserId(UUID.fromString(userId));
        List<Prescription> prescriptions = prescriptionList.stream()
                .filter(Prescription::isActive)
                .toList();
        List<PrescriptionDto> prescriptionDtoList = new ArrayList<>();
        if (prescriptions.isEmpty()) {
            return Collections.emptyList();
        } else {
            for (Prescription prescription : prescriptions) {
                prescriptionDtoList.add(prescriptionMapper.toDto(prescription));
            }
        }
        return prescriptionDtoList;
    }
}
