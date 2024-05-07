package com.rangers.medicineservice.mapper.util;

import com.rangers.medicineservice.dto.CreateVisitResponseDto;
import com.rangers.medicineservice.entity.Schedule;
import com.rangers.medicineservice.entity.enums.AppointmentType;
import com.rangers.medicineservice.util.DateTimeFormat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface CreateAppointmentMapper {

    default CreateVisitResponseDto generateResponse(Schedule schedule) {
        CreateVisitResponseDto createVisitResponseDto = new CreateVisitResponseDto();
        createVisitResponseDto.setDoctorName(schedule.getDoctor().getFirstName() + " " + schedule.getDoctor().getLastName());
        createVisitResponseDto.setUserName(schedule.getUser().getFirstname() + " " + schedule.getUser().getLastname());
        if (schedule.getType() == AppointmentType.ONLINE){
            createVisitResponseDto.setLinkOrAddress(schedule.getLink());
        } else createVisitResponseDto.setLinkOrAddress("Main Street, 5, Berlin, Clinic 'Healthy Life'");
        createVisitResponseDto.setDateTime(DateTimeFormat.formatLocalDateTime(schedule.getDateTime()));
        return createVisitResponseDto;
    }
}
