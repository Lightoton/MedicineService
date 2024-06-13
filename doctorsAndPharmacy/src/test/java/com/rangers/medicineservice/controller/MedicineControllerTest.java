package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.TestConfig;
import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.service.impl.MedicineServiceImpl;
import com.rangers.medicineservice.testUtil.ExpectedData;
import lombok.RequiredArgsConstructor;
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

@SpringBootTest(classes = TestConfig.class)
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
        List<MedicineDto> expected = ExpectedData.getExpectedMedicines();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/medicine/getAvailable"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        List<MedicineDto> actual = objectMapper.readValue(json, new TypeReference<>() {
        });
//        Assertions.assertTrue(actual.size() == 3);

        Assertions.assertTrue(expected.size() == actual.size() && expected.containsAll(actual));
    }

    @Test
    void getAvailableTestExc400() throws Exception {
        medicineService.resetQuantity();

        mockMvc.perform(MockMvcRequestBuilders.get("/medicine/getAvailable"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getByCategoryPositiveTest() throws Exception {
        List<MedicineDto> expected = ExpectedData.getExpectedMedicineByCategory();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/medicine/getByCategory/ANTIHISTAMINES"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        List<MedicineDto> actual = objectMapper.readValue(json, new TypeReference<>() {
        });

        Assertions.assertTrue(expected.size() == actual.size() && expected.containsAll(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/medicine/getByCategory/VITAMINS_AND_SUPPLEMENTS",
            "/medicine/getByCategory/NON_EXISTING_CATEGORY"
    })
    void getByCategoryTestExc400(String path) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(path))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getByNamePositiveTest() throws Exception {
        Medicine expected = ExpectedData.getExpectedMedicine();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/medicine/getByName/Claritin"))
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Medicine actual = objectMapper.readValue(json, new TypeReference<>() {
        });
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByNameTestExc404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/medicine/getByName/Non-existing_name"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}