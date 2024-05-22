package com.rangers.medicineservice.utils.formater;

import com.rangers.medicineservice.dto.CancelVisitResponseDto;
import com.rangers.medicineservice.dto.ScheduleFullDto;
import org.springframework.stereotype.Service;

@Service
public class ScheduleFormat {
    public String getScheduleFormat(ScheduleFullDto scheduleFullDto) {
        String link = scheduleFullDto.getLink();
        return "-Your appointment with the doctor: " + scheduleFullDto.getDoctorName() + ".\n"
                + "-Doctor's specialization: " + scheduleFullDto.getDoctorSpecialization() + ".\n"
                + "-Date and Time: " + scheduleFullDto.getDateAndTime() + ".\n"
                + "-Visit format: " + scheduleFullDto.getAppointmentType() + ".\n"
                + (link != null && !link.isEmpty() ? "-Link: " + link + ".\n" : "")
                + "-Status: " + scheduleFullDto.getStatus();
    }
    public String getCancelScheduleFormat(CancelVisitResponseDto cancelVisitResponseDto) {

        return "-Your appointment with the doctor: " + cancelVisitResponseDto.getDoctorFullName() + ".\n"
                + "-Date and Time: " + cancelVisitResponseDto.getDateTime() + ".\n"
                + cancelVisitResponseDto.getAnswer();
    }
}
