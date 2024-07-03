package com.rangers.medicineservice.controller;

import com.rangers.medicineservice.annotation.user.*;
import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.dto.CreatedCartItemDto;
import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.service.interf.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CreateUserDocumentation(path = "/registration")
    //http://localhost:8080/user/registration
    public UserAfterRegistrationDto createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return userService.createUser(userRegistrationDto);
    }

    @GetUserByIdDocumentation(path = "/{id}")
    //http://localhost:8080/user/72971ae6-58e3-4081-9095-06742628dab1
    public UserInfoDto getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @GetUserIdByChatIdDocumentation(path = "/chatId/{chatId}")
    public String getUserIdByChatId(@PathVariable("chatId") String chatId) {
        return userService.getUserIdByChatId(chatId);
    }

    @UpdateUserDocumentation(path = "/update")
    public UserInfoDto updateUser(@RequestBody UserInfoDto userInfoDto) {
        return userService.updateUser(userInfoDto);
    }

    @GetUserHistoryOrdersDocumentation(path = "/history/orders/userId/{id}")
    //http://localhost:8080/user/history/orders/userId/ddb7ccab-9f3d-409d-a7ab-9573061c6e29
    public List<UserHistoryOrdersDto> getUserHistoryOrders(@PathVariable("id") String id) {
        return userService.getUserHistoryOrders(id);
    }

    @GetMapping("/history/schedules/userId/{id}")
    public List<ScheduleFullDto> getUserHistorySchedules(@PathVariable("id") String id){
        return userService.getUserHistorySchedules(id);
    }

    @GetMapping("/history/prescriptions/userId/{id}")
    public List<UserHistoryPrescriptionsDto> getUserHistoryPrescriptions(@PathVariable("id") String id){
        return userService.getUserHistoryPrescriptions(id);
    }

    @GetMapping("/prescriptions/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user prescriptions", description = "Get list of user prescriptions by userId")
    @ApiResponse(
            responseCode = "200",
            description = "***Prescriptions returned successfully***",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreatedCartItemDto.class))
            })
    public List<PrescriptionDto> getUserPrescription(@PathVariable UUID id) {
        return userService.getUserPrescriptions(id);
    }

}
