package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.TestConfig;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.testUtil.ExpectedData;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Sql("/db/drop-tables.sql")
@Sql("/db/create-tables.sql")
@Sql("/db/insert-tables.sql")
class PrescriptionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPrescriptionPositiveTest() throws Exception{
        Prescription expected = ExpectedData.getExpectedPrescription();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/prescription/getPrescription/ac4c4444-176f-4754-1357-f52cbb9b4a95"))
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Prescription actual = objectMapper.readValue(json, new TypeReference<>() {});
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getPrescriptionDtoPositiveTest() throws Exception{
        PrescriptionDto expected = ExpectedData.getExpectedPrescriptionDto();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/prescription/getPrescriptionDto/ac5c9927-676f-4714-2357-f52cbb9b4a95"))
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        PrescriptionDto actual = objectMapper.readValue(json, new TypeReference<>() {});
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getActivePositiveTest() throws Exception{
        List<PrescriptionDto> expected = List.of(ExpectedData.getExpectedPrescriptionDto());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/prescription/getActive/ac5c8867-676f-4737-931f-052cbb9b4a59"))
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List<PrescriptionDto> actual = objectMapper.readValue(json, new TypeReference<>() {});
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getActiveTestListIsEmpty() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/prescription/getActive/ac5c8867-676f-4737-931f-052cbb9b4a11"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List<PrescriptionDto> actual = objectMapper.readValue(json, new TypeReference<>() {});
        Assertions.assertTrue(actual.isEmpty());
    }
}