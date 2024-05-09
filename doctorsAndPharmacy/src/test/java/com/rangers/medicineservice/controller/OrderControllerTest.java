package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.dto.CreatedOrderDto;
import com.rangers.medicineservice.entity.CartItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/drop-tables.sql")
@Sql("/db/create-tables.sql")
@Sql("/db/insert-tables.sql")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder() throws Exception {
        CartItem item = new CartItem();
        item.setCartItemId(UUID.fromString("e7ecbc11-0064-4764-9872-aae30692cf7f"));
        item.setQuantity(3);
        Set<CartItem> expectedCartItems = new HashSet<>();
        expectedCartItems.add(item);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedCartItems)))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        CreatedOrderDto createdOrderDto = objectMapper.readValue(jsonResponse, CreatedOrderDto.class);

        assert createdOrderDto != null;
    }
}