package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.ScheduleDateTimeDto;
import com.rangers.medicineservice.dto.ScheduleFullDto;
import com.rangers.medicineservice.entity.Schedule;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface ScheduleMapper {

    ScheduleDateTimeDto toDto(Schedule schedule);

    @AfterMapping
    default void updateDto(Schedule schedule, @MappingTarget ScheduleDateTimeDto scheduleDto) {
        scheduleDto.setDateAndTime(schedule.getDateAndTime());
    }

    ScheduleFullDto toFullDto(Schedule schedule);
    @AfterMapping
    default void updateFullDto(Schedule schedule, @MappingTarget ScheduleFullDto scheduleFullDto) {
        scheduleFullDto.setDateAndTime(schedule.getDateAndTime().toLocalDate().toString()+ " " + schedule.getDateAndTime().toLocalTime());
        scheduleFullDto.setLink(schedule.getLink());
        scheduleFullDto.setAppointmentType(schedule.getType().toString());
        scheduleFullDto.setStatus(schedule.getStatus().toString());
        scheduleFullDto.setDoctorName(schedule.getDoctor().getFirstName()+" "+schedule.getDoctor().getLastName());
        scheduleFullDto.setDoctorSpecialization(schedule.getDoctor().getSpecialization().toString());
    }
}
