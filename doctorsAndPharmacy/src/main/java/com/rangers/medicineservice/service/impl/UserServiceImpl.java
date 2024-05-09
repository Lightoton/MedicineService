package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.*;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.entity.Order;
import com.rangers.medicineservice.exception.UserExistException;
import com.rangers.medicineservice.exception.UserNotFoundException;
import com.rangers.medicineservice.exception.errorMessage.ErrorMessage;
import com.rangers.medicineservice.mapper.OrderMapper;
import com.rangers.medicineservice.mapper.UserMapper;
import com.rangers.medicineservice.repository.OrderRepository;
import com.rangers.medicineservice.repository.UserRepository;
import com.rangers.medicineservice.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;

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
        return orderMapper.toUserHistoryOrdersDto(orders);
    }

    @Override
    public UserHistorySchedulesDto getUserHistorySchedules(String id) {
        return null;
    }

    @Override
    public UserHistoryPrescriptionsDto getUserHistoryPrescriptions(String id) {
        return null;
    }
}
