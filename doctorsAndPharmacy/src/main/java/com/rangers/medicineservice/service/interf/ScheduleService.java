package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    CreateVisitResponseDto createVisit(String schedule_id, CreateVisitRequestDto createVisitRequestDto);

    CancelVisitResponseDto cancelVisit(String scheduleId, CancelVisitRequestDto cancelVisitRequestDto);

    Schedule findById(UUID id);

    List<ScheduleDateTimeDto> getScheduleDate(UUID doctorId);

    List<ScheduleDateTimeDto> getScheduleTime(UUID doctorId, String date);

    ScheduleFullDto getSchedule(UUID doctorId, String dateAndTime);
}
