package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.TestConfig;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.User;
import org.junit.jupiter.api.Assertions;
import com.rangers.medicineservice.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
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
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@DisplayName("Test class for UserController")
@Sql("/db/drop-tables.sql")
@Sql("/db/create-tables.sql")
@Sql("/db/insert-tables.sql")
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    User expected = new User();

    @Autowired
    private UserRepository userRepository;

    private static final String userTestId = "ddb7ccab-9f3d-409d-a7ab-9573061c6e29";


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
        prescriptionDto.setPrescriptionId("ac5c8867-676f-4754-1357-f74cbb9b4a96");
        prescriptionDto.setExpiryDate(LocalDate.parse("2024-11-25"));
        prescriptionDto.setUserId("ac5c8867-676f-4737-931f-052cbb9b4a59");
        List<PrescriptionDto> expectedList = new ArrayList<>();
        expectedList.add(prescriptionDto);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/user/prescriptions/ac5c8867-676f-4737-931f-052cbb9b4a59")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<PrescriptionDto> actualList = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void createUserTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstname": "Test firstname of user",
                                  "lastname": "Test lastname of user",
                                  "email": "test email",
                                  "phoneNumber": "test phoneNumber",
                                  "address": "test address",
                                  "city": "test city",
                                  "country": "test country",
                                  "postalCode": "51000",
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
        assertEquals(userTestId, userIdActual);
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
    void getUserHistoryOrdersTest() {
    }

    @Test
    void getUserHistorySchedulesTest() {
    }

    @Test
    void getUserHistoryPrescriptionsTest() {
    }
}
