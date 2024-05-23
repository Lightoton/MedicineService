package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.ScheduleDateTimeDto;
import com.rangers.medicineservice.dto.ScheduleFullDto;
import com.rangers.medicineservice.entity.Schedule;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

    ScheduleDateTimeDto toDto(Schedule schedule);

    @AfterMapping
    default void updateDto(Schedule schedule, @MappingTarget ScheduleDateTimeDto scheduleDto) {
        scheduleDto.setDateAndTime(schedule.getDateTime());
    }

    ScheduleFullDto toFullDto(Schedule schedule);
    @AfterMapping
    default void updateFullDto(Schedule schedule, @MappingTarget ScheduleFullDto scheduleFullDto) {
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("dd-MM HH:mm");
        scheduleFullDto.setScheduleId(schedule.getScheduleId());
        scheduleFullDto.setDateAndTime(schedule.getDateTime().format(formatterTime));
        scheduleFullDto.setStatus(schedule.getStatus().toString());
        scheduleFullDto.setDoctorName(schedule.getDoctor().getFirstName()+" "+schedule.getDoctor().getLastName());
        scheduleFullDto.setDoctorSpecialization(schedule.getDoctor().getSpecialization().toString());
        if (schedule.getType() != null) {
            scheduleFullDto.setAppointmentType(schedule.getType().toString());
        } else {
            scheduleFullDto.setAppointmentType(null);
        }
    }

    List<ScheduleFullDto> toFullDtoList(List<Schedule> schedules);

}
