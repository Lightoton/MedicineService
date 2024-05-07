package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.annotation.GetScheduleByDoctorAndDateAndTimeMapping;
import com.rangers.medicineservice.annotation.GetScheduleByDoctorAndDateMapping;
import com.rangers.medicineservice.annotation.GetScheduleByDoctorMapping;
import com.rangers.medicineservice.dto.ScheduleDateTimeDto;
import com.rangers.medicineservice.dto.ScheduleFullDto;
import com.rangers.medicineservice.service.interfaces.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Tag(name = "Schedule's info", description = "Interaction with schedule information.")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetScheduleByDoctorMapping(path = "/get_schedule_by_Doctor/{doctorId}")
    public List<ScheduleDateTimeDto> getScheduleByDoctor(@PathVariable UUID doctorId) {
        return scheduleService.getScheduleDate(doctorId);
    }

    @GetScheduleByDoctorAndDateMapping(path = "/get_schedule_time_by_Doctor_and_Date/{doctorId}")
    public List<ScheduleDateTimeDto> getScheduleTimeByDoctorAndDate(@PathVariable UUID doctorId, @RequestBody String date) {
        date = date.replace("\"", "");
        return scheduleService.getScheduleTime(doctorId, date);
    }

    @GetScheduleByDoctorAndDateAndTimeMapping(path = "/get_schedule_by_Doctor_and_date_time/{doctorId}")
    public ScheduleFullDto getScheduleByDoctorAndDateTime(@PathVariable UUID doctorId, @RequestBody String dateAndTime) {
        dateAndTime = dateAndTime.replace("\"", "");
        return scheduleService.getSchedule(doctorId, dateAndTime);
    }
}
