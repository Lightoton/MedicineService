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

@Sql("/drop.sql")
@Sql("create-tables-changelog.sql")
@Sql("insert_test_data.sql")


@SpringBootTest
@AutoConfigureMockMvc
class StockingPrescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addOrder() throws Exception {
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

    private MvcResult gelAllOrders() throws Exception {
        return mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/orders/all")).andReturn();
    }
}