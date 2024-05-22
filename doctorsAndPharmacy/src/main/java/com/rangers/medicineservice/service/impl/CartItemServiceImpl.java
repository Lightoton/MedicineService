package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.dto.CartItemBeforeCreationDto;
import com.rangers.medicineservice.dto.CreatedCartItemDto;
import com.rangers.medicineservice.entity.CartItem;
import com.rangers.medicineservice.entity.Medicine;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.exception.ObjectDoesNotExistException;
import com.rangers.medicineservice.exception.errorMessage.ErrorMessage;
import com.rangers.medicineservice.exeption.QuantityCantBeLowerThenOneException;
import com.rangers.medicineservice.mapper.CartItemMapper;
import com.rangers.medicineservice.repository.CartItemRepository;
import com.rangers.medicineservice.repository.MedicineRepository;
import com.rangers.medicineservice.repository.UserRepository;
import com.rangers.medicineservice.service.interf.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper mapper;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;

    @Override
    @Transactional
    public CreatedCartItemDto createCartItem(CartItemBeforeCreationDto cartItemDto) {
        CartItem cartItem = mapper.toEntity(cartItemDto);
        cartItem.setUser(userRepository.findById(UUID.fromString(cartItemDto.getUserId())).orElse(null));
        cartItem.setMedicine(medicineRepository.findById(UUID.fromString(cartItemDto.getMedicineId())).orElse(null));
        if (cartItem.getQuantity() < 1) throw new QuantityCantBeLowerThenOneException("Invalid value");
        return mapper.toDto(cartItemRepository.saveAndFlush(cartItem));
    }

    @Override
    @Transactional
    public void deleteCartItem(UUID cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        assert cartItem != null;
        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public List<CartItem> getCartItemsByUserId(String id) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ObjectDoesNotExistException(ErrorMessage.USER_NOT_FOUND));
        return cartItemRepository.getAllByUser(user);
    }

    @Override
    @Transactional
    public void deleteAllByMedicineAndUser(String medicineId, String userId) {
        Medicine medicine = medicineRepository.findById(UUID.fromString(medicineId))
                .orElseThrow(() -> new ObjectDoesNotExistException(ErrorMessage.THERE_IS_NO_SUCH_MEDICINE));
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ObjectDoesNotExistException(ErrorMessage.USER_NOT_FOUND));
        cartItemRepository.deleteAllByMedicineAndUser(medicine, user);
    }
}


