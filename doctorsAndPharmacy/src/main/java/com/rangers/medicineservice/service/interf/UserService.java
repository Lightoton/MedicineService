package com.rangers.medicineservice.service.interf;

import com.rangers.medicineservice.dto.PrescriptionDto;

import com.rangers.medicineservice.dto.*;
import java.util.List;

public interface UserService {
    List<PrescriptionDto> getUserPrescriptions(UUID id);
    UserAfterRegistrationDto createUser(UserRegistrationDto userRegistrationDto);
    UserInfoDto getUserById(String id);
    String getUserIdByChatId(String chatId);
    UserInfoDto updateUser(UserInfoDto userInfoDto);
    List<UserHistoryOrdersDto> getUserHistoryOrders(String id);
    UserHistorySchedulesDto getUserHistorySchedules(String id);
    UserHistoryPrescriptionsDto getUserHistoryPrescriptions(String id);
}
