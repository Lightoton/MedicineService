package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.PrescriptionDto;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.Schedule;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.mapper.PrescriptionMapper;
import com.rangers.medicineservice.entity.Order;
import com.rangers.medicineservice.exception.*;
import com.rangers.medicineservice.exception.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.OrderMapper;
import com.rangers.medicineservice.mapper.PrescriptionMapper;
import com.rangers.medicineservice.mapper.ScheduleMapper;
import com.rangers.medicineservice.mapper.UserMapper;
import com.rangers.medicineservice.repository.OrderRepository;
import com.rangers.medicineservice.repository.PrescriptionRepository;
import com.rangers.medicineservice.repository.ScheduleRepository;
import com.rangers.medicineservice.repository.UserRepository;
import com.rangers.medicineservice.service.interf.OrderService;
import com.rangers.medicineservice.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The {@code UserServiceImpl} class implements the {@link UserService} interface and provides
 * methods to create users , update user, get userId by chatId, and get History Orders
 * History Schedules, HistoryPrescriptions of user.
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PrescriptionMapper prescriptionMapper;
    private final OrderRepository orderRepository;
    private final ScheduleRepository scheduleRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final ScheduleMapper scheduleMapper;

    @Override
    public List<PrescriptionDto> getUserPrescriptions(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        List<PrescriptionDto> prescriptionDtoList = new ArrayList<>();
        if (user == null || user.getPrescriptions().isEmpty()) {
            return Collections.emptyList();
        } else {
            for (Prescription prescription : user.getPrescriptions()) {
                prescriptionDtoList.add(prescriptionMapper.toDto(prescription));
            }
        }
        return prescriptionDtoList;
    }

    @Override
    public UserAfterRegistrationDto createUser(UserRegistrationDto userRegistrationDto) {
        if (userRegistrationDto.getChatId().isEmpty()){
            throw new UserNotFoundException(ErrorMessage.CHAT_ID_MUST_BE_PRESENT);
        }
        User userByChatId = userRepository.getUserByChatId(userRegistrationDto.getChatId());
        if (userByChatId!=null) {
            throw new UserExistException(ErrorMessage.USER_WITH_CHAT_ID_EXIST);
        }

        User user = userMapper.toEntityFromUserRegistrationDto(userRegistrationDto);
        user.setChatId(userRegistrationDto.getChatId());
        return userMapper.toAfterRegistrationDto(userRepository.save(user));
    }

    @Override
    public UserInfoDto getUserById(String id) {
        return userMapper.toDto(userRepository.findById(UUID.fromString(id))
                .orElseThrow(()-> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND)));
    }

    @Override
    public String getUserIdByChatId(String chatId) {
        User user = userRepository.getUserByChatId(chatId);
        if (user != null) {
            return user.getUserId().toString();
        } else {
            throw new UserNotFoundException(ErrorMessage.USER_WITH_CHAT_ID_NOT_FOUND);
        }
    }

    @Override
    public UserInfoDto updateUser(UserInfoDto userInfoDto) {
        Optional<User> userOptional = userRepository.findById(userInfoDto.getUserId());
        if (userOptional.isEmpty()) throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
        User user = userOptional.get();
        if (userInfoDto.getFirstname()!=null) user.setFirstname(userInfoDto.getFirstname());
        if (userInfoDto.getLastname()!=null) user.setLastname(userInfoDto.getLastname());
        if (userInfoDto.getEmail()!=null) user.setEmail(userInfoDto.getEmail());
        if (userInfoDto.getPhoneNumber()!=null) user.setPhoneNumber(userInfoDto.getPhoneNumber());
        if (userInfoDto.getAddress()!=null) user.setAddress(userInfoDto.getAddress());
        if (userInfoDto.getCity()!=null) user.setCity(userInfoDto.getCity());
        if (userInfoDto.getCountry()!=null) user.setCountry(userInfoDto.getCountry());
        if (userInfoDto.getPostalCode()!=null) user.setPostalCode(userInfoDto.getPostalCode());
        if (userInfoDto.getPolicyNumber()!=null) user.setPolicyNumber(userInfoDto.getPolicyNumber());
        return userMapper.toDto(userRepository.saveAndFlush(user));
    }

    @Override
    public List<UserHistoryOrdersDto> getUserHistoryOrders(String id) {
        List<Order> orders  = orderRepository.getOrdersByUserId(UUID.fromString(id));
        if (orders.isEmpty()) {
            throw new OrderNotFoundException(ErrorMessage.ORDERS_NOT_FOUND);
        }
        return orderMapper.toUserHistoryOrdersDto(orders);
    }

    @Override
    public List<ScheduleFullDto> getUserHistorySchedules(String id) {
        List<Schedule> schedules  = scheduleRepository.findByUserId(UUID.fromString(id));
        if (schedules.isEmpty()) {
            throw new ScheduleNotFoundException(ErrorMessage.SCHEDULES_NOT_FOUND);
        }
        return scheduleMapper.toFullDtoList(schedules);
    }

    @Override
    public List<UserHistoryPrescriptionsDto> getUserHistoryPrescriptions(String id) {
        List<Prescription> prescriptions  = prescriptionRepository.findByUserId(UUID.fromString(id));
        if (prescriptions.isEmpty()) {
            throw new PrescriptionNotFoundException(ErrorMessage.PRESCRIPTIONS_NOT_FOUND);
        }
        return prescriptionMapper.toUserHistoryPrescriptionsDtoList(prescriptions);
    }
}
