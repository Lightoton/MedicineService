package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.CancelVisitRequestDto;
import com.rangers.medicineservice.dto.CancelVisitResponseDto;
import com.rangers.medicineservice.dto.CreateVisitRequestDto;
import com.rangers.medicineservice.dto.CreateVisitResponseDto;
import com.rangers.medicineservice.entity.Schedule;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ScheduleService {
    CreateVisitResponseDto createVisit(String schedule_id, CreateVisitRequestDto createVisitRequestDto);

    CancelVisitResponseDto cancelVisit(String scheduleId, CancelVisitRequestDto cancelVisitRequestDto);

    Schedule findById(UUID id);
}
