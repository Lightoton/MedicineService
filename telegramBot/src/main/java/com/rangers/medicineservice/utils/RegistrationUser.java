package com.rangers.medicineservice.utils;

import com.rangers.medicineservice.service.impl.UserServiceImpl;
import org.springframework.stereotype.Service;
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
