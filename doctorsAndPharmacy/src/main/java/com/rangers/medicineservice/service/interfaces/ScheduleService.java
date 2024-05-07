package com.rangers.medicineservice.service.interfaces;

import com.rangers.medicineservice.dto.ScheduleDateTimeDto;
import com.rangers.medicineservice.dto.ScheduleFullDto;
import com.rangers.medicineservice.entity.Schedule;

import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    List<ScheduleDateTimeDto> getScheduleDate(UUID doctorId);

    List<ScheduleDateTimeDto> getScheduleTime(UUID doctorId, String date);

    ScheduleFullDto getSchedule(UUID doctorId, String dateAndTime);
}
