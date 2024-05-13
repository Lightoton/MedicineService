package com.rangers.medicineservice.utils;

import com.rangers.medicineservice.entity.enums.Specialization;
import com.rangers.medicineservice.service.impl.DoctorServiceImpl;
import com.rangers.medicineservice.service.impl.ScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class GetButtons {
    private static DoctorServiceImpl service;
    public List<List<InlineKeyboardButton>> getListsStartMenu = getListsStartMenu();
    public List<List<InlineKeyboardButton>> getListsSchedule = getListsSchedule();



    public static List<List<InlineKeyboardButton>> getListsStartMenu() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton("Make an appointment with a doctor");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Pharmacy");
        InlineKeyboardButton button3 = new InlineKeyboardButton("Get ZoomLink");
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
}
