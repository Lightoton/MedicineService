package com.rangers.medicineservice.utils;

import com.rangers.medicineservice.dto.DoctorDto;
import com.rangers.medicineservice.dto.MedicineDto;
import com.rangers.medicineservice.dto.ScheduleDateTimeDto;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.entity.enums.MedicineCategory;
import com.rangers.medicineservice.entity.enums.Specialization;
import com.rangers.medicineservice.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetButtons {
    public static DoctorServiceImpl service;
    public static ScheduleServiceImpl scheduleService;
    public List<List<InlineKeyboardButton>> getListsScheduleType = getListsScheduleType();
    public List<List<InlineKeyboardButton>> getListsStartMenu = getListsStartMenu();
    public List<List<InlineKeyboardButton>> getListsSchedule = getListsSchedule();

    public static MedicineServiceImpl medicineService;
    public static CartItemServiceImpl cartItemService;
    public static UserServiceImpl userService;

    @Autowired
    public void setMedicineService(MedicineServiceImpl medicineService) {
        GetButtons.medicineService = medicineService;
    }

    @Autowired
    public void setCartItemService(CartItemServiceImpl cartItemService) {
        GetButtons.cartItemService = cartItemService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        GetButtons.userService = userService;
    }

    public GetButtons(DoctorServiceImpl service, ScheduleServiceImpl scheduleService, MedicineServiceImpl medicineService) {
        GetButtons.service = service;
        GetButtons.scheduleService = scheduleService;
        GetButtons.medicineService = medicineService;
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
    public List<List<InlineKeyboardButton>> getListsScheduleType(){
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

    public static List<List<InlineKeyboardButton>> getYesNoButtons(String nameYes, String nameNo, String callBackYes, String callBackNo){
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton(nameYes);
        InlineKeyboardButton button2 = new InlineKeyboardButton(nameNo);
        button1.setCallbackData(callBackYes);
        button2.setCallbackData(callBackNo);
        rowInline.add(button1);
        rowInline.add(button2);
        rowsInline.add(rowInline);
        return rowsInline;
    }

    public static List<List<InlineKeyboardButton>> getMedicineCategoryButtons(){
        List<String> categories = Arrays.stream(MedicineCategory.values()).map(Enum::toString).toList();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (String category : categories) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(category);
            button.setCallbackData("category:" + category);
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        return rowsInline;
    }

    public static List<List<InlineKeyboardButton>> getListsMedicines(String category) {
        List<MedicineDto> medicineList = medicineService.getByCategory(category);
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (MedicineDto medicine : medicineList) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(medicine.getName() + " price: " +
                    medicine.getPrice());
            button.setCallbackData("medicine:" + medicine.getName());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        return rowsInline;
    }

    public static List<List<InlineKeyboardButton>> getCart(String userId){
        List<CartItem> cartItems = cartItemService.getCartItemsByUserId(userId);


//        List<CartItem> newCartItems = cartItems.stream()
//                .collect(Collectors.groupingBy(CartItem::getMedicine))
//                .entrySet().stream()
//                .map(entry -> {
//                    Medicine medicine = entry.getKey();
//                    int totalQuantity = entry.getValue().stream()
//                            .mapToInt(CartItem::getQuantity)
//                            .sum();
//                    User user = entry.getValue().get(0).getUser(); // Предполагаем, что user одинаков для всех
//                    CartItem cartItem = new CartItem();
//                    cartItem.setMedicine(medicine);
//                    cartItem.setUser(user);
//                    cartItem.setQuantity(totalQuantity);
//                    return new CartItem();
//                })
//                .collect(Collectors.toList());

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(
                    cartItem.getMedicine().getName()
                            + ". price: " + cartItem.getMedicine().getPrice()
                            + ", quantity: " + cartItem.getQuantity());
            button.setCallbackData("delete item:" + cartItem.getCartItemId());
            rowInline.add(button);
            rowsInline.add(rowInline);
        }
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton buttonCheckout = new InlineKeyboardButton("CHECKOUT");
        buttonCheckout.setCallbackData("checkout");
        rowInline.add(buttonCheckout);
        rowsInline.add(rowInline);
        return rowsInline;
    }
}
