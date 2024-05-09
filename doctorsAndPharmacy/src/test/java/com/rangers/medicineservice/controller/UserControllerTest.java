package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/drop-tables.sql")
@Sql("/db/create-tables.sql")
@Sql("/db/insert-tables.sql")
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    User expected = new User();

    @Test
    void getUserPrescription() throws Exception {
        expected.setUserId(UUID.fromString("ac5c8867-676f-4737-931f-052cbb9b4a11"));

        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(UUID.fromString("ac5c9927-676f-4714-2357-f52cbb9b4a95"));
        prescription.setActive(true);
        prescription.setCreatedAt(LocalDate.parse("2023-11-25"));
        prescription.setExpDate(LocalDate.parse("2024-11-25"));


        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);
        expected.setPrescriptions(prescriptions);

        PrescriptionDto prescriptionDto = new PrescriptionDto();
        prescriptionDto.setPrescriptionId("ac5c9927-676f-4714-2357-f52cbb9b4a95");
        prescriptionDto.setActive(true);
        prescriptionDto.setCreatedAt("2023-11-25");
        prescriptionDto.setExpDate("2024-11-25");
        prescriptionDto.setDoctorName("John Doe");
        List<PrescriptionDto> expectedList = new ArrayList<>();
        expectedList.add(prescriptionDto);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/user/prescriptions/ac5c8867-676f-4737-931f-052cbb9b4a11")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<PrescriptionDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(expectedList, actualList);
    }
}