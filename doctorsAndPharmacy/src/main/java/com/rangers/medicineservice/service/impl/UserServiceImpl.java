package com.rangers.medicineservice.service.impl;

import com.rangers.medicineservice.entity.Prescription;
import com.rangers.medicineservice.entity.User;
import com.rangers.medicineservice.repository.UserRepository;
import com.rangers.medicineservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<Prescription> getUserPrescriptions(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        return user.getPrescriptions();
    }
}
