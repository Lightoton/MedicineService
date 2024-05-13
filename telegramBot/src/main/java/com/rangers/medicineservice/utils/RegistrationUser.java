package com.rangers.medicineservice.utils;

import com.rangers.medicineservice.config.ChatBot;
import com.rangers.medicineservice.dto.UserRegistrationDto;
import com.rangers.medicineservice.service.impl.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class RegistrationUser{
    private final UserServiceImpl userService;

    public RegistrationUser(UserServiceImpl userService) {
        this.userService = userService;
    }


    public boolean isHaveUser(String chatId) {
        try {
            return userService.getUserIdByChatId(chatId) != null;
        } catch (Exception e) {
            return false;
        }
    }
//    public void startRegistration(String chatId) {
//        sendMsg(chatId, "Привет! Давай начнем регистрацию. Введи свое имя:");
//        isRegistrationInProgress.put(chatId, true);
//    }
//    public void handleRegistration(String messageText, String chatId) {
//        switch (registrationStep.get(chatId)) {
//            case 0:
//                users.get(chatId).setFirstname(messageText);
//                sendMsg.sendMsg(chatId, "Отлично! Теперь введи свою фамилию:");
//                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
//                break;
//            case 1:
//                users.get(chatId).setLastname(messageText);
//                sendMsg.sendMsg(chatId, "Отлично! Теперь введи свою электронную почту:");
//                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
//                break;
//            case 2:
//                users.get(chatId).setEmail(messageText);
//                sendMsg.sendMsg(chatId, "Отлично! Теперь введи свой номер телефона:");
//                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
//                break;
//            case 3:
//                users.get(chatId).setPhoneNumber(messageText);
//                sendMsg.sendMsg(chatId, "Отлично! Теперь введи свой адресс:");
//                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
//                break;
//            case 4:
//                users.get(chatId).setAddress(messageText);
//                sendMsg.sendMsg(chatId, "Отлично! Теперь введи город проживания:");
//                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
//                break;
//            case 5:
//                users.get(chatId).setCity(messageText);
//                sendMsg.sendMsg(chatId, "Отлично! Теперь введи страну проживания:");
//                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
//                break;
//            case 6:
//                users.get(chatId).setCountry(messageText);
//                sendMsg.sendMsg(chatId, "Отлично! Теперь введи почтовый индекс:");
//                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
//                break;
//            case 7:
//                users.get(chatId).setPostalCode(messageText);
//                sendMsg.sendMsg(chatId, "Отлично! Теперь введи номер страховки:");
//                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
//                break;
//            case 8:
//                users.get(chatId).setPolicyNumber(messageText);
//                users.get(chatId).setChatId(chatId);
//                sendMsg.sendMsg(chatId, "Отлично! Регестрация завершена!!!");
//                userService.createUser(users.get(chatId));
//                isRegistrationInProgress.put(chatId, false);
//                break;
//        }
//
//    }


    //    private void handleRegistration(String messageText, String chatId) {
    //        switch (registrationStep.get(chatId)) {
    //            case 0:
    //                users.get(chatId).setFirstname(messageText);
    //                sendMsg(chatId, "Отлично! Теперь введи свою фамилию:");
    //                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
    //                break;
    //            case 1:
    //                users.get(chatId).setLastname(messageText);
    //                sendMsg(chatId, "Отлично! Теперь введи свою электронную почту:");
    //                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
    //                break;
    //            case 2:
    //                users.get(chatId).setEmail(messageText);
    //                sendMsg(chatId, "Отлично! Теперь введи свой номер телефона:");
    //                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
    //                break;
    //            case 3:
    //                users.get(chatId).setPhoneNumber(messageText);
    //                sendMsg(chatId, "Отлично! Теперь введи свой адресс:");
    //                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
    //                break;
    //            case 4:
    //                users.get(chatId).setAddress(messageText);
    //                sendMsg(chatId, "Отлично! Теперь введи город проживания:");
    //                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
    //                break;
    //            case 5:
    //                users.get(chatId).setCity(messageText);
    //                sendMsg(chatId, "Отлично! Теперь введи страну проживания:");
    //                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
    //                break;
    //            case 6:
    //                users.get(chatId).setCountry(messageText);
    //                sendMsg(chatId, "Отлично! Теперь введи почтовый индекс:");
    //                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
    //                break;
    //            case 7:
    //                users.get(chatId).setPostalCode(messageText);
    //                sendMsg(chatId, "Отлично! Теперь введи номер страховки:");
    //                registrationStep.put(chatId, registrationStep.get(chatId) + 1);
    //                break;
    //            case 8:
    //                users.get(chatId).setPolicyNumber(messageText);
    //                users.get(chatId).setChatId(chatId);
    //                sendMsg(chatId, "Отлично! Регестрация завершена!!!");
    //                userService.createUser(users.get(chatId));
    //                isRegistrationInProgress.put(chatId, false);
    //                break;
    //        }
    //
    //    }
    //
//    public void startRegistration(String chatId) {
//            sendMsg(chatId, "Привет! Давай начнем регистрацию. Введи свое имя:");
//            isRegistrationInProgress.put(chatId, true);
//        }
}
