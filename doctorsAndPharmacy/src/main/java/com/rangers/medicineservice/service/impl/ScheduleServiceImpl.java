package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.ScheduleDateTimeDto;
import com.rangers.medicineservice.dto.ScheduleFullDto;
import com.rangers.medicineservice.entity.Schedule;
import com.rangers.medicineservice.exeption.ScheduleNotFoundException;
import com.rangers.medicineservice.exeption.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.ScheduleMapper;
import com.rangers.medicineservice.repository.ScheduleRepository;
import com.rangers.medicineservice.service.interfaces.ScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    @Qualifier("scheduleMapper")
    private final ScheduleMapper mapper;


    @Override
    @Transactional
    public List<ScheduleDateTimeDto> getScheduleDate(UUID doctorId) {
        List<Schedule> schedules = scheduleRepository.findByDoctor(doctorId);
        return getScheduleDateTimeDtos(schedules);
    }

    @Override
    @Transactional
    public List<ScheduleDateTimeDto> getScheduleTime(UUID doctorId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Schedule> schedules = scheduleRepository.findByDoctorIdAndDate(doctorId, LocalDate.parse(date,formatter));
        return getScheduleDateTimeDtos(schedules);
    }

    @NotNull
    private List<ScheduleDateTimeDto> getScheduleDateTimeDtos(List<Schedule> schedules) {
        if (schedules.isEmpty()) {
            throw new ScheduleNotFoundException(ErrorMessage.SCHEDULE_NOT_FOUND);
        }
        List<ScheduleDateTimeDto> dateAndTime = new ArrayList<>();
        for (Schedule schedule : schedules) {
            dateAndTime.add(mapper.toDto(schedule));
        }
        return dateAndTime;
    }

    @Override
    @Transactional
    public ScheduleFullDto getSchedule(UUID doctorId, String dateAndTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Schedule schedule = scheduleRepository.findByDoctorIdAndDateAndTime(doctorId, LocalDateTime.parse(dateAndTime, formatter));
        if (schedule == null) {throw new ScheduleNotFoundException(ErrorMessage.SCHEDULE_NOT_FOUND);}
        return mapper.toFullDto(schedule);
    }
}
