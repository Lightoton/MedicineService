package com.rangers.medicineservice.mapper;

import com.rangers.medicineservice.dto.ScheduleDateTimeDto;
import com.rangers.medicineservice.dto.ScheduleFullDto;
import com.rangers.medicineservice.entity.Schedule;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
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
        scheduleFullDto.setScheduleId(schedule.getScheduleId());
        scheduleFullDto.setDateAndTime(schedule.getDateTime().toLocalDate().toString()+ " " + schedule.getDateTime().toLocalTime());
        scheduleFullDto.setStatus(schedule.getStatus().toString());
        scheduleFullDto.setDoctorName(schedule.getDoctor().getFirstName()+" "+schedule.getDoctor().getLastName());
        scheduleFullDto.setDoctorSpecialization(schedule.getDoctor().getSpecialization().toString());
    }

    List<ScheduleFullDto> toFullDtoList(List<Schedule> schedules);

}
