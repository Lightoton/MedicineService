package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.exception.UserNotFoundException;
import com.rangers.medicineservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.apache.logging.log4j.ThreadContext.isEmpty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(classes= User.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test class for UserController")
@Sql("/db/drop-tables.sql")
@Sql("/db/create-tables.sql")
@Sql("/db/insert-tables.sql")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private static final String userTestId = "ddb7ccab-9f3d-409d-a7ab-9573061c6e29";

    @Test
    void createUserTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstname": "Test firstname of user",
                                  "lastname": "Test lastname of user",
                                  "email": "email",
                                  "phoneNumber": "phoneNumber",
                                  "address": "address",
                                  "city": "city",
                                  "country": "country",
                                  "postalCode": "postalCode",
                                  "policyNumber": "25449599043",
                                  "chatId": "099"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is("Test firstname of user")))
                .andExpect(jsonPath("$.lastname", is("Test lastname of user")))
                .andExpect(jsonPath("$.policyNumber", is("25449599043")));
        UUID userId = userRepository.getUserByPolicyNumber("25449599043").getUserId();
        userRepository.deleteById(userId);
        Optional<User> findUser = userRepository.findById(userId);
        assertFalse(findUser.isPresent());
    }

    @Test
    void getUserByIdTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/user/" + userTestId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(userTestId)));
    }

    @Test
    void getUserIdByChatIdTest() throws Exception {
        String userIdActual = mockMvc
                .perform(MockMvcRequestBuilders.get("/user/chatId/001"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals(userTestId,userIdActual);
    }

    @Test
    void updateUserTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.put("/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": "ddb7ccab-9f3d-409d-a7ab-9573061c6e29",
                                  "firstname": "New",
                                  "lastname": "User",
                                  "policyNumber": "345543"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is("New")))
                .andExpect(jsonPath("$.lastname", is("User")))
                .andExpect(jsonPath("$.policyNumber", is("345543")));
        mockMvc
                .perform(MockMvcRequestBuilders.put("/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": "ddb7ccab-9f3d-409d-a7ab-9573061c6e29",
                                  "firstname": "Hans",
                                  "lastname": "Anderson",
                                  "policyNumber": "1234567890"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is("Hans")))
                .andExpect(jsonPath("$.lastname", is("Anderson")))
                .andExpect(jsonPath("$.policyNumber", is("1234567890")));
    }

    @Test
    void getUserHistoryOrdersTest() throws Exception {
//        mockMvc
//                .perform(MockMvcRequestBuilders.get("/user/history/orders/userId/"+userTestId))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) jsonPath("$.orderId", not(isEmpty())))
//                .andExpect((ResultMatcher) jsonPath("$.quantity", not(isEmpty())))
//                .andExpect((ResultMatcher) jsonPath("$.name", not(isEmpty())));
    }

    @Test
    void getUserHistorySchedulesTest() {
    }

    @Test
    void getUserHistoryPrescriptionsTest() {
    }
}