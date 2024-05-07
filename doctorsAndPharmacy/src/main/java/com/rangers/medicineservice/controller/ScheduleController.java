package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.annotation.CancelVisit;
import com.rangers.medicineservice.annotation.CreateVisit;
import com.rangers.medicineservice.dto.CancelVisitRequestDto;
import com.rangers.medicineservice.dto.CancelVisitResponseDto;
import com.rangers.medicineservice.dto.CreateVisitRequestDto;
import com.rangers.medicineservice.dto.CreateVisitResponseDto;
import com.rangers.medicineservice.service.interf.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @CreateVisit(path = "/create/{schedule_id}")
    public CreateVisitResponseDto createVisit(@RequestBody CreateVisitRequestDto createVisitRequestDto,
                                              @PathVariable(name = "schedule_id") String schedule_id){
        return scheduleService.createVisit(schedule_id, createVisitRequestDto);
    }

    @CancelVisit(path = "/cancel/{schedule_id}")
    public CancelVisitResponseDto cancelVisit(@RequestBody CancelVisitRequestDto cancelVisitRequestDto,
                                              @PathVariable(name = "schedule_id") String schedule_id){
        return scheduleService.cancelVisit(schedule_id, cancelVisitRequestDto);
    }

}
