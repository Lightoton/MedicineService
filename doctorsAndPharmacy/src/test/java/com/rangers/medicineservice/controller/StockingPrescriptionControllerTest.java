package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.controller.util.Generator;
import com.rangers.medicineservice.entity.Order;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/db/drop.sql")
@Sql("/db/create-tables-changelog.sql")
@Sql("/db/insert_test_data.sql")


@SpringBootTest
@AutoConfigureMockMvc
class StockingPrescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showAllOrdersTest() throws Exception {

        MvcResult orderResult = gelAllOrders();
        String orderAfterResultJSON = orderResult.getResponse().getContentAsString();
        List<Order> orderListAfter = objectMapper.readValue(orderAfterResultJSON, new TypeReference<>() {
        });

        Assertions.assertEquals(1, orderListAfter.size());
    }

    @Test
    void addOrderTest() throws Exception {
        String prescription = Generator.getNewPrescription();

        MvcResult orderBeforeResult = gelAllOrders();

        String orderResultJSON = orderBeforeResult.getResponse().getContentAsString();
        List<Order> orderListBefore = objectMapper.readValue(orderResultJSON, new TypeReference<>() {
        });

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/add").contentType(MediaType.APPLICATION_JSON)
                .content(prescription));

        MvcResult orderAfterResult = gelAllOrders();
        String orderAfterResultJSON = orderAfterResult.getResponse().getContentAsString();
        List<Order> orderListAfter = objectMapper.readValue(orderAfterResultJSON, new TypeReference<>() {
        });

        Assertions.assertEquals(orderListBefore.size() + 1, orderListAfter.size());
    }

    @Test
    void NegativeStrangerPrescriptionTest() throws Exception {

        String prescription = Generator.getWrongPrescription();

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prescription))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "message": "The prescription belongs to another user",
                          "statusCode": 400
                        }"""));
    }

    @Test
    void NegativeExpiredPrescriptionTest() throws Exception {

        String prescription = Generator.getExpiredPrescription();

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/add").contentType(MediaType.APPLICATION_JSON)
                .content(prescription)).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "message": "The prescription has expired.",
                          "statusCode": 400
                        }"""));

    }

    @Test
    void NegativeInactivePrescriptionTest() throws Exception {

        String prescription = Generator.getInactivePrescription();

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/add").contentType(MediaType.APPLICATION_JSON)
                .content(prescription)).andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "message": "The prescription has already been cashed.",
                          "statusCode": 400
                        }"""));

    }

    @Test
    void NegativeEmptyPrescriptionTest() throws Exception {

        String prescription = Generator.getEmptyPrescription();

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/add").contentType(MediaType.APPLICATION_JSON)
                .content(prescription)).andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "message": "The prescription is empty",
                          "statusCode": 400
                        }"""));
    }

    @Test
    void NegativeBigQuantityPrescriptionTest() throws Exception {

        String prescription = Generator.getNegativeBalancePrescription();

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/add").contentType(MediaType.APPLICATION_JSON)
                .content(prescription)).andExpect(status().isBadRequest())
                .andExpect(content().json("""
                        {
                          "message": "Not enough balance, maximum quantity for Amoxicillin is 0",
                          "statusCode": 400
                        }"""));
    }


    private MvcResult gelAllOrders() throws Exception {
        return mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/orders/all")).andReturn();
    }
}