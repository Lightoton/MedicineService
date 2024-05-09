package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.repository.MedicineRepository;
import com.rangers.medicineservice.service.impl.MedicineServiceImpl;
import com.rangers.medicineservice.service.interf.MedicineService;
import com.rangers.medicineservice.testUtil.ExpectedData;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Sql("/db/drop-tables.sql")
@Sql("/db/create-tables.sql")
@Sql("/db/insert-tables.sql")
class MedicineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MedicineServiceImpl medicineService;


    @Test
    void getAvailablePositiveTest() throws Exception {
        List<MedicineDto> expected = ExpectedData.getExpectedMedicine();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/medicine/getAvailable"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        List<MedicineDto> actual = objectMapper.readValue(json, new TypeReference<>() {});

        Assertions.assertTrue(expected.size() == actual.size() && expected.containsAll(actual));
    }

    @Test
    void getAvailableTestExc400() throws Exception{
        medicineService.resetQuantity();

        mockMvc.perform(MockMvcRequestBuilders.get("/medicine/getAvailable"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}