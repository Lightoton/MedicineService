package com.rangers.medicineservice.service.impl;
import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.entity.Schedule;
import com.rangers.medicineservice.exception.ScheduleNotFoundException;
import com.rangers.medicineservice.exception.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.ScheduleMapper;
import com.rangers.medicineservice.repository.ScheduleRepository;
import com.rangers.medicineservice.service.interf.ScheduleService;
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

import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.entity.enums.AppointmentType;
import com.rangers.medicineservice.entity.enums.ScheduleStatus;
import com.rangers.medicineservice.exception.*;
import com.rangers.medicineservice.mapper.util.CreateAppointmentMapper;
import com.rangers.medicineservice.repository.DoctorRepository;
import com.rangers.medicineservice.repository.UserRepository;
import com.rangers.medicineservice.util.DateTimeFormat;
import com.rangers.medicineservice.util.ZoomUtil;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    @Qualifier("scheduleMapper")
    private final ScheduleMapper mapper;

    private final UserRepository userRepository;
    private final CreateAppointmentMapper createAppointmentMapper;
    private final DoctorRepository doctorRepository;


    @Override
    @Transactional
    public List<ScheduleDateTimeDto> getScheduleDate(UUID doctorId) {
        List<Schedule> schedules = scheduleRepository.findByDoctor(doctorId);
        return getScheduleDateTimeDtos(schedules);
    }

    @Override
    @Transactional
    public CreateVisitResponseDto createVisit(String schedule_id, CreateVisitRequestDto createVisitRequestDto) {
        Optional<User> optionalUser = userRepository.findById(UUID.fromString(createVisitRequestDto.getUser_id()));
        User user;
        if (optionalUser.isEmpty()) {
            throw new UserDoesNotExistException("Please login");
        } else user = optionalUser.get();

        Schedule schedule = scheduleRepository.findByScheduleId(UUID.fromString(schedule_id));
        if (schedule == null) {
            throw new ScheduleDoesNotExistException(ErrorMessage.SCHEDULE_DOES_NOT_EXIST);
        } else if (schedule.getDoctor() == null) {
            throw new ScheduleDoesNotHaveDoctorException(ErrorMessage.SCHEDULE_DOES_NOT_HAVE_A_DOCTOR);
        } else if (schedule.getUser() != null) {
            throw new TimeIsBusyException(ErrorMessage.TIME_IS_BUSY);
        } else {
            schedule.setUser(user);
            schedule.setType(AppointmentType.valueOf(createVisitRequestDto.getAppointmentType()));
            if (schedule.getType() == AppointmentType.ONLINE) {
                schedule.setLink(ZoomUtil.generateZoomLink());
            }
            schedule.setStatus(ScheduleStatus.IN_PROGRESS);
        }
        return createAppointmentMapper.generateResponse(scheduleRepository.saveAndFlush(schedule));
    }

    @Override
    @Transactional
    public List<ScheduleDateTimeDto> getScheduleTime(UUID doctorId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Schedule> schedules = scheduleRepository.findByDoctorIdAndDate(doctorId, LocalDate.parse(date, formatter));
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
    public CancelVisitResponseDto cancelVisit(String scheduleId, CancelVisitRequestDto cancelVisitRequestDto) {
        Optional<User> user = userRepository.findById(UUID.fromString(cancelVisitRequestDto.getUserId()));
        if (user.isEmpty()){
            throw new UserDoesNotExistException("Please login");
        }
        CancelVisitResponseDto cancelVisitResponseDto = new CancelVisitResponseDto();
        Schedule schedule = scheduleRepository.findByScheduleId(UUID.fromString(scheduleId));
        if (schedule.getUser() == null || !schedule.getUser().getUserId().equals(UUID.fromString(cancelVisitRequestDto.getUserId()))){
            throw new YouDoNotHaveAnAppointmentWithThisDoctorException(ErrorMessage
                    .YOU_DO_NOT_HAVE_AN_APPOINTMENT_WITH_THIS_DOCTOR);
        } else {
            cancelVisitResponseDto.setUserFullName(schedule.getUser().getFirstname() + " " + schedule.getUser().getLastname());
            schedule.setUser(null);
        }
        cancelVisitResponseDto.setDoctorFullName(schedule.getDoctor().getFirstName() + " " + schedule.getDoctor().getLastName());
        cancelVisitResponseDto.setDateTime(DateTimeFormat.formatLocalDateTime(schedule.getDateTime()));
        return cancelVisitResponseDto;
    }

        @Override
        @Transactional
        public ScheduleFullDto getSchedule (UUID doctorId, String dateAndTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Schedule schedule = scheduleRepository.findByDoctorIdAndDateAndTime(doctorId, LocalDateTime.parse(dateAndTime, formatter));
            if (schedule == null) {
                throw new ScheduleNotFoundException(ErrorMessage.SCHEDULE_NOT_FOUND);
            }
            return mapper.toFullDto(schedule);
        }
        @Override
        @Transactional
        public Schedule findById (UUID id){
                return scheduleRepository.findByScheduleId(id);
            }
        }


