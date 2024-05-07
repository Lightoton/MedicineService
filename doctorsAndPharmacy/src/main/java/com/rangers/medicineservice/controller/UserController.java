package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.annotation.user.*;
import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CreateUserDocumentation(path = "/registration")
    //http://localhost:8080/user/registration
    public UserAfterRegistrationDto createUser (@RequestBody UserRegistrationDto userRegistrationDto){
        return userService.createUser(userRegistrationDto);
    }

    @GetUserByIdDocumentation(path = "/{id}")
    //http://localhost:8080/user/72971ae6-58e3-4081-9095-06742628dab1
    public UserInfoDto getUserById(@PathVariable("id") String id){
        return userService.getUserById(id);
    }

    @GetUserIdByChatIdDocumentation(path = "/chatId/{chatId}")
    public String getUserIdByChatId(@PathVariable("chatId") String chatId){
        return userService.getUserIdByChatId(chatId);
    }

    @UpdateUserDocumentation(path = "/update")
    public UserInfoDto updateUser(@RequestBody UserInfoDto userInfoDto){
        return userService.updateUser(userInfoDto);
    }

    @GetUserHistoryOrdersDocumentation(path = "/history/orders/userId/{id}")
    public List<UserHistoryOrdersDto> getUserHistoryOrders(@PathVariable("id") String id){
        return userService.getUserHistoryOrders(id);
    }

    @GetMapping("/history/schedules/userId/{id}")
    public UserHistorySchedulesDto getUserHistorySchedules(@PathVariable("id") String id){
        return userService.getUserHistorySchedules(id);
    }

    @GetMapping("/history/prescriptions/userId/{id}")
    public UserHistoryPrescriptionsDto getUserHistoryPrescriptions(@PathVariable("id") String id){
        return userService.getUserHistoryPrescriptions(id);
    }
}
