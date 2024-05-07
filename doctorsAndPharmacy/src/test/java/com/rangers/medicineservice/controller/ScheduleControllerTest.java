package com.rangers.medicineservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rangers.medicineservice.dto.ScheduleDateTimeDto;
import com.rangers.medicineservice.dto.ScheduleFullDto;
import com.rangers.medicineservice.entity.Schedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/db/drop-tables.sql")
@Sql("/db/create-tables.sql")
@Sql("/db/insert-tables.sql")
class ScheduleControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final UUID doctorId = UUID.fromString("01f558a1-736b-4916-b7e8-02a06c63ac7a");
    private final List<String> dateAndTimes = List.of("2024-11-23T15:00", "2024-11-23T10:00");


    @Test
    void getScheduleByDoctorPositiveTest() throws Exception {
        List<ScheduleDateTimeDto> schedules = new ArrayList<>();
        ScheduleDateTimeDto scheduleDateTimeDto = new ScheduleDateTimeDto();
        ScheduleDateTimeDto scheduleDateTimeDto2 = new ScheduleDateTimeDto();
        scheduleDateTimeDto.setDateAndTime(LocalDateTime.parse(dateAndTimes.getFirst()));
        scheduleDateTimeDto2.setDateAndTime(LocalDateTime.parse(dateAndTimes.getLast()));
        schedules.add(scheduleDateTimeDto);
        schedules.add(scheduleDateTimeDto2);
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/schedule/get_schedule_by_Doctor/" + doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        List<ScheduleDateTimeDto> responseSchedules = objectMapper.readValue(response, new TypeReference<>() {
        });

        Assertions.assertEquals(schedules, responseSchedules);
    }

    @Test
    void getScheduleByDoctorNegativeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/schedule/get_schedule_by_Doctor/01f558a1-736b-4916-b7e8-02a06c63bc7a"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "message": "Schedule Not Found",
                          "errorCode": "NOT_FOUND"
                        }"""));
    }

    @Test
    void getScheduleTimeByDoctorAndDatePositiveTest() throws Exception {
        ScheduleDateTimeDto scheduleDateTimeDto = new ScheduleDateTimeDto();
        scheduleDateTimeDto.setDateAndTime(LocalDateTime.parse(dateAndTimes.getFirst()));
        String date = "2024-11-23";
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/schedule/get_schedule_time_by_Doctor_and_Date/" + doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(date))
                .andExpect(status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        List<ScheduleDateTimeDto> responseSchedules = objectMapper.readValue(response, new TypeReference<>() {
        });
        Assertions.assertEquals(scheduleDateTimeDto, responseSchedules.getFirst());
    }

    @Test
    void getScheduleTimeByDoctorAndDateNegativeTest() throws Exception {
        String falseDate = "2023-11-15";
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/schedule/get_schedule_time_by_Doctor_and_Date/" + doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(falseDate))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "message": "Schedule Not Found",
                          "errorCode": "NOT_FOUND"
                        }"""));
    }

    @Test
    void getScheduleByDoctorAndDateTimePositiveTest() throws Exception {
        ScheduleFullDto scheduleFullDto = new ScheduleFullDto();
        scheduleFullDto.setDoctorSpecialization("THERAPIST");
        scheduleFullDto.setDoctorName("Michael Johnson");
        scheduleFullDto.setStatus("FREE");
        scheduleFullDto.setDateAndTime("2024-11-23 15:00");
        String dateAndTime = "2024-11-23 15:00:00";

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/schedule/get_schedule_by_Doctor_and_date_time/" + doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dateAndTime))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ScheduleFullDto responseSchedule = objectMapper.readValue(response, new TypeReference<>() {
        });
        Assertions.assertEquals(scheduleFullDto, responseSchedule);
    }

    @Test
    void getScheduleByDoctorAndDateTimeNegativeTest() throws Exception {
        String dateAndTime = "2023-11-23 15:00:00";

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/schedule/get_schedule_by_Doctor_and_date_time/" + doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dateAndTime))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "message": "Schedule Not Found",
                          "errorCode": "NOT_FOUND"
                        }"""));
    }
}