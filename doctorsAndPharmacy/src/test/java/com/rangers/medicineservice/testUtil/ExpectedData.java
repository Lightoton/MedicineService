package com.rangers.medicineservice.testUtil;

import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.entity.enums.MedicineCategory;

import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static List<MedicineDto> getExpectedMedicines(){
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

    public static List<CartItem> getExpectedCartItems() {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(UUID.fromString("e7ecbc11-0064-4764-9872-aae30692cf7f"));
        Medicine medicine = new Medicine();
        medicine.setMedicineId(UUID.fromString("8bda1395-2ee3-4aee-80c1-842bedd9f4c2"));
        cartItem.setMedicine(medicine);
        User user = new User();
        user.setUserId(UUID.fromString("ac5c8867-676f-4737-931f-052cbb9b4a11"));
        cartItem.setUser(user);
        cartItem.setQuantity(1);
        return List.of(cartItem);
    }

    public static Medicine getExpectedMedicine(){
        Medicine medicine = new Medicine();
        medicine.setMedicineId(UUID.fromString("01f558a1-736b-4916-b7e8-02a06c63ac8a"));
        medicine.setName("Claritin");
        medicine.setDescription("Non-drowsy antihistamine for seasonal allergy relief.");
        medicine.setPrice(BigDecimal.valueOf(8.99));
        medicine.setCategory(MedicineCategory.ANTIHIAMINES);
        return medicine;
    }

    public static Prescription getExpectedPrescription() {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(UUID.fromString("ac4c4444-176f-4754-1357-f52cbb9b4a95"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createdAt = LocalDateTime.parse("2023-11-25 17:00:00", formatter);
        prescription.setCreatedAt(LocalDate.from(createdAt));
        LocalDateTime expDate = LocalDateTime.parse("2025-11-26 17:00:00", formatter);
        prescription.setExpDate(LocalDate.from(expDate));
        prescription.setActive(true);
        return prescription;
    }

    public static PrescriptionDto getExpectedPrescriptionDto() {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        prescriptionDto.setPrescriptionId("ac5c9927-676f-4714-2357-f52cbb9b4a95");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expDate = LocalDate.parse("2024-11-25", formatter);
        prescriptionDto.setExpiryDate(expDate);
        return prescriptionDto;
    }
}
