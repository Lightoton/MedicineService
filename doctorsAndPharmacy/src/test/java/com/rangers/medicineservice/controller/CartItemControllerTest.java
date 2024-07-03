package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.TestConfig;
import com.rangers.medicineservice.dto.CartItemBeforeCreationDto;
import com.rangers.medicineservice.dto.CreatedCartItemDto;
import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.service.impl.CartItemServiceImpl;
import com.rangers.medicineservice.service.interf.CartItemService;
import com.rangers.medicineservice.testUtil.ExpectedData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@Sql("/db/drop-tables.sql")
@Sql("/db/create-tables.sql")
@Sql("/db/insert-tables.sql")
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    static CartItem expected = new CartItem();
    @Autowired
    private CartItemServiceImpl cartItemService;

    @BeforeAll
    static void setUp() {
        expected.setCartItemId(UUID.fromString("e7ecbc11-0064-4764-9872-aae30692cf7f"));
    }

    @Test
    void createCartItem() throws Exception {
        CartItemBeforeCreationDto beforeCreationDto = new CartItemBeforeCreationDto(
                "8bda1395-2ee3-4aee-80c1-842bedd9f4c2",
                "ac5c8867-676f-4737-931f-052cbb9b4a11",
                1
        );
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/cart/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beforeCreationDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        CreatedCartItemDto createdCartItem = objectMapper.readValue(jsonResponse, CreatedCartItemDto.class);

        assert createdCartItem != null;
        assert createdCartItem.getCartItemId() != null;
    }

    @Test
    void deleteCartItem() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cart/delete/" + expected.getCartItemId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();

        assert responseContent.equals("***Deleted successfully!***");
    }

    @Test
    void getCartItemsByUserIdPositiveTest() throws Exception {
        List<CartItem> expected = ExpectedData.getExpectedCartItems();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cart/getCart/ac5c8867-676f-4737-931f-052cbb9b4a11"))
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List<CartItem> actual = objectMapper.readValue(json, new TypeReference<>() {
        });

        Assertions.assertTrue(expected.size() == actual.size() && expected.containsAll(actual));
    }

    @Test
    void getCartItemsByUserIdTestExc404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart/getCart/ac5c8867-676f-4737-931f-052cbb9b4a12"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void DeleteAllCartItemsByMedicineAndUserPositiveTest() throws Exception{
        List<CartItem> before = getList();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cart/delete/8bda1395-2ee3-4aee-80c1-842bedd9f4c2/ac5c8867-676f-4737-931f-052cbb9b4a11"))
                .andExpect(status().isOk());
        List<CartItem> after = getList();
        Assertions.assertTrue(!before.isEmpty() && after.isEmpty());
    }

    @Test
    void DeleteAllCartItemsByMedicineAndUserTestExc404() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cart/delete/8bda1395-2ee3-4aee-80c1-842bedd9f4c2/ac5c8867-676f-4737-931f-052cbb9b4a12"))
                .andExpect(status().isNotFound());
    }

    List<CartItem> getList(){
        Medicine medicine = new Medicine();
        medicine.setMedicineId(UUID.fromString("8bda1395-2ee3-4aee-80c1-842bedd9f4c2"));
        User user = new User();
        user.setUserId(UUID.fromString("ac5c8867-676f-4737-931f-052cbb9b4a11"));
        return cartItemService.getAllByMedicineAndUser(medicine, user);
    }
}
