package com.rangers.medicineservice.testUtil;

import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.entity.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpectedData {

    public static List<DoctorDto> getExpectedDoctors(){
        DoctorDto doctor1 = new DoctorDto();
        doctor1.setFullName("Alice Smith");
        doctor1.setSpecialization("FAMILY_DOCTOR");

        DoctorDto doctor2 = new DoctorDto();
        doctor2.setFullName("John Doe");
        doctor2.setSpecialization("FAMILY_DOCTOR");

        return List.of(doctor1, doctor2);
    }

    public static List<MedicineDto> getExpectedMedicine(){
        MedicineDto medicineDto1 = new MedicineDto();
        medicineDto1.setId("01f558a1-736b-4916-b7e8-02a06c63ac8a");
        medicineDto1.setName("Claritin");
        medicineDto1.setDescription("Non-drowsy antihistamine for seasonal allergy relief.");
        medicineDto1.setPrice("8.99");
        medicineDto1.setCategory("ANTIHIAMINES");
        medicineDto1.setAvailableQuantity("25");

        MedicineDto medicineDto2 = new MedicineDto();
        medicineDto2.setId("8bda1395-2ee3-4aee-80c1-842bedd9f4c2");
        medicineDto2.setName("Ibuprofen");
        medicineDto2.setDescription("NSAID for pain relief, inflammation reduction, and fever lowering.");
        medicineDto2.setPrice("9.29");
        medicineDto2.setCategory("PAIN_RELIEVERS");
        medicineDto2.setAvailableQuantity("38");

        MedicineDto medicineDto3 = new MedicineDto();
        medicineDto3.setId("ac5c8867-676f-4737-931f-052cbb9b4a84");
        medicineDto3.setName("SleepEase");
        medicineDto3.setDescription("Gentle sleep aid with natural ingredients for restful sleep.");
        medicineDto3.setPrice("19.99");
        medicineDto3.setCategory("SEDATIVES");
        medicineDto3.setAvailableQuantity("50");

        return List.of(medicineDto1, medicineDto2, medicineDto3);
    }

    public static List<MedicineDto> getExpectedMedicineByCategory(){
        MedicineDto medicineDto1 = new MedicineDto();
        medicineDto1.setId("01f558a1-736b-4916-b7e8-02a06c63ac8a");
        medicineDto1.setName("Claritin");
        medicineDto1.setDescription("Non-drowsy antihistamine for seasonal allergy relief.");
        medicineDto1.setPrice("8.99");
        medicineDto1.setCategory("ANTIHIAMINES");
        medicineDto1.setAvailableQuantity("25");
        return List.of(medicineDto1);
    }

}
