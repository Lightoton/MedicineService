package com.rangers.medicineservice.utils;

import com.rangers.medicineservice.service.impl.UserServiceImpl;
import org.springframework.stereotype.Service;
/**
 * Class for checking if a user is registered.
 * @author Viktor
 */
@Service
public class RegistrationUser{
    private final UserServiceImpl userService;

    public RegistrationUser(UserServiceImpl userService) {
        this.userService = userService;
    }
    /**
     * Checks if a user with the given chat ID is registered.
     * @author Viktor
     * @param chatId The ID of the chat.
     * @return True if the user is registered, false otherwise.
     */
    public boolean isHaveUser(String chatId) {
        try {
            return userService.getUserIdByChatId(chatId) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
