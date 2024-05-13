package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.testUtil.ExpectedData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/drop-tables.sql")
@Sql("/db/create-tables.sql")
@Sql("/db/insert-tables.sql")

class DoctorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getDoctorsPositiveTest() throws Exception {
        List<DoctorDto> expected = ExpectedData.getExpectedDoctors();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/doctor/getDoctors/FAMILY_DOCTOR"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        List<DoctorDto> actual = objectMapper.readValue(json, new TypeReference<>() {});

        Assertions.assertTrue(expected.size() == actual.size() && expected.containsAll(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/doctor/getDoctors/PEDIATRIC",
            "/doctor/getDoctors/NON_EXIST_SPECIALIZATION"
    })
    void getDoctorsTestExc400(String path) throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(path))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}