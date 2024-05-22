package com.rangers.medicineservice.utils;

import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.dto.ScheduleDateTimeDto;
import com.rangers.medicineservice.dto.ScheduleFullDto;
import com.rangers.medicineservice.entity.enums.Specialization;
import com.rangers.medicineservice.service.impl.DoctorServiceImpl;
import com.rangers.medicineservice.service.impl.ScheduleServiceImpl;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GetButtons {
    public static DoctorServiceImpl service;
    public static ScheduleServiceImpl scheduleService;
    public static final Map<String, List<ScheduleFullDto>> scheduleByUser = new ConcurrentHashMap<>();

    public GetButtons(DoctorServiceImpl service, ScheduleServiceImpl scheduleService) {
        GetButtons.service = service;
        GetButtons.scheduleService = scheduleService;
    }

    public static List<List<InlineKeyboardButton>> getListsStartMenu() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton("Make an appointment with a doctor");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Pharmacy");
        InlineKeyboardButton button3 = new InlineKeyboardButton("Virtual assistant");
        button1.setCallbackData("start1");
        button2.setCallbackData("start2");
        button3.setCallbackData("start3");
        rowInline1.add(button1);
        rowInline.add(button2);
        rowInline.add(button3);
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline);
        return rowsInline;
    }
    public static List<List<InlineKeyboardButton>> getListsSchedule() {
        List<String> specializations = Arrays.stream(Specialization.values()).map(Enum::toString).toList();


        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (String specialization : specializations) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton("Make an appointment with " + specialization);
            button.setCallbackData("specialization:" + specialization); // Указываем специализацию в callbackData
            rowInline.add(button);
            rowsInline.add(rowInline);
        }

        return rowsInline;
    }
    public static List<List<InlineKeyboardButton>> getListsDoctors(String specializations) {
        List<DoctorDto> doctorDtos = service.getDoctors(specializations);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (DoctorDto dto : doctorDtos) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton("Doctor: " + dto.getFullName()
                    + " Rating: " + dto.getRating());
            button.setCallbackData("Doctor:" + dto.getUuid());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }

        return rowsInline;
    }
    public static List<List<InlineKeyboardButton>> getListsDatesByDoctor(String doctorId) {
        List<ScheduleDateTimeDto> date = scheduleService.getScheduleDate(UUID.fromString(doctorId));
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (ScheduleDateTimeDto dto : date) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton("Date: " + dto.getDateAndTime().toLocalDate().toString());
            button.setCallbackData("Date:" + dto.getDateAndTime().toLocalDate());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }

        return rowsInline;
    }
    public static List<List<InlineKeyboardButton>> getListsTimesByDoctorAndDate(String doctorId,String date) {
        List<ScheduleDateTimeDto> time = scheduleService.getScheduleTime(UUID.fromString(doctorId),date);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (ScheduleDateTimeDto dto : time) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton("Time: " + dto.getDateAndTime().toLocalTime());
            button.setCallbackData("Time:" + dto.getDateAndTime().toLocalTime());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }

        return rowsInline;
    }
    public static List<List<InlineKeyboardButton>> getListsScheduleType(){
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton("ONLINE");
        InlineKeyboardButton button2 = new InlineKeyboardButton("OFFLINE");
        button1.setCallbackData("type:" + button1.getText());
        button2.setCallbackData("type:" + button2.getText());
        rowInline.add(button1);
        rowInline.add(button2);
        rowsInline.add(rowInline);
        return rowsInline;
    }

    public static List<List<InlineKeyboardButton>> getListsSchedulesActiveByUser(String uuid) {
        List<ScheduleFullDto> schedules = scheduleService.getSchedulesByUserInProgress(UUID.fromString(uuid));
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        scheduleByUser.put(uuid, schedules);

        for (ScheduleFullDto dto : schedules) {
            if (dto != null) {
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                InlineKeyboardButton scheduleButton = new InlineKeyboardButton("Date and time: " + dto.getDateAndTime()
                        + ", " + dto.getDoctorSpecialization());
                scheduleButton.setCallbackData("ScheduleUser:" + dto.getScheduleId());
                rowInline.add(scheduleButton);
                rowsInline.add(rowInline);
            }
        }
        return rowsInline;
    }

    public static List<List<InlineKeyboardButton>> getCancelButtonForSchedule(String scheduleId) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton cancelButton = new InlineKeyboardButton("Cancel");
        cancelButton.setCallbackData("CancelSchedule:" + scheduleId);
        rowInline.add(cancelButton);

        rowsInline.add(rowInline);

        return rowsInline;
    }
    public static List<List<InlineKeyboardButton>> getApproveCancelButtonForSchedule() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton yes = new InlineKeyboardButton("yes");
        InlineKeyboardButton no = new InlineKeyboardButton("no");
        yes.setCallbackData("ApproveCancel:" + yes.getText());
        no.setCallbackData("ApproveCancel:" + no.getText());
        rowInline.add(yes);
        rowInline.add(no);

        rowsInline.add(rowInline);

        return rowsInline;
    }
}
