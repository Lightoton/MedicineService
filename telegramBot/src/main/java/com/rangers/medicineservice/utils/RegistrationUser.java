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
}
