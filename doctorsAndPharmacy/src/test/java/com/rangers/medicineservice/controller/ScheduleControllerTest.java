package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.entity.Schedule;
import com.rangers.medicineservice.service.impl.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Sql("/db/drop-tables.sql")
@Sql("/db/create-tables.sql")
@Sql("/db/insert_test_data.sql")
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    private final String json1 = """
            {
              "user_id" : "ac5c8867-676f-4737-931f-052cbb9b4a59",
              "appointmentType" : "OFFLINE"
            }""";

    private final String json2 = """
            {
              "user_id" : "ac5c8867-676f-4737-931f-052cbb9b4a51",
              "appointmentType" : "OFFLINE"
            }""";


    @Test
    void createVisitPositiveTest() throws Exception {
        Schedule scheduleBefore = scheduleService
                .findById(UUID.fromString("f4a7bf08-de17-4195-ac57-fe251d9e15c2"));

        String jsonBody = """
                {
                    "user_id" : "ac5c8867-676f-4737-931f-052cbb9b4a59",
                    "appointmentType" : "OFFLINE"
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/schedule/create/f4a7bf08-de17-4195-ac57-fe251d9e15c2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Your visit was created")))
                .andExpect(content().string(containsString("20.11.2024 11:00")))
                .andExpect(content().string(containsString("Alice Smith")))
                .andExpect(content().string(containsString("Mikle Ivanov")))
                .andExpect(content().string(containsString("Main Street, 5, Berlin, Clinic 'Healthy Life'")));

        Schedule scheduleAfter = scheduleService
                .findById(UUID.fromString("f4a7bf08-de17-4195-ac57-fe251d9e15c2"));

        assertNull(scheduleBefore.getUser());
        assertEquals(scheduleAfter.getUser().getUserId(), UUID.fromString("ac5c8867-676f-4737-931f-052cbb9b4a59"));
    }

    @ParameterizedTest
    @CsvSource({
            "/schedule/create/18d62c9d-d863-4bb2-b7f4-c1dcf6921161, json1",
            "/schedule/create/18d62c9d-d863-4bb2-b7f4-c1dcf692116e, json2"
    })
    void createVisitTestExc404(String path, String jsonBodyVariable) throws Exception{

        String jsonBody = null;

        if ("json1".equals(jsonBodyVariable)) {
            jsonBody = json1;
        } else if ("json2".equals(jsonBodyVariable)) {
            jsonBody = json2;
        } else {
            throw new IllegalArgumentException("Invalid JSON body variable: " + jsonBodyVariable);
        }
        mockMvc.perform(MockMvcRequestBuilders
                        .put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/schedule/create/ac5c8867-676f-4737-931f-052cbb9b4a94",
            "/schedule/create/18d62c9d-d863-4bb2-b7f4-c1dcf692116e"
    })
    void createVisitTestExc400(String path) throws Exception{

        String jsonBody = """
                {
                    "user_id" : "ac5c8867676f4737931f052cbb9b4a11",
                    "appointmentType" : "OFFLINE"
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cancelVisitPositiveTest()  throws Exception{
        Schedule scheduleBefore = scheduleService
                .findById(UUID.fromString("ac5c8867-676f-4737-931f-052cbb9b4a95"));

        String jsonBody = """
                {
                    "userId" : "ac5c8867-676f-4737-931f-052cbb9b4a59"
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/schedule/cancel/ac5c8867-676f-4737-931f-052cbb9b4a95")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Your visit was canceled")))
                .andExpect(content().string(containsString("Mikle Ivanov")))
                .andExpect(content().string(containsString("25.11.2024 17:00")))
                .andExpect(content().string(containsString("John Doe")));

        Schedule scheduleAfter = scheduleService
                .findById(UUID.fromString("ac5c8867-676f-4737-931f-052cbb9b4a95"));

        assertEquals(scheduleBefore.getUser().getUserId(), UUID.fromString("ac5c8867-676f-4737-931f-052cbb9b4a59"));
        assertNull(scheduleAfter.getUser());
    }

    @Test
    void cancelVisitTestExc404()  throws Exception{
        String jsonBody = """
                {
                    "userId" : "ac5c8867-676f-4737-931f-052cbb9b4a50"
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/schedule/cancel/ac5c8867-676f-4737-931f-052cbb9b4a95")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void cancelVisitTestExc400()  throws Exception{
        String jsonBody = """
                {
                    "userId" : "ac5c8867-676f-4737-931f-052cbb9b4a59"
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/schedule/cancel/33c8d879-47e6-4f71-9743-fd83c2983fe2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}