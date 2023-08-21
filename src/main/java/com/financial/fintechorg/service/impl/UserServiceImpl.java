package com.financial.fintechorg.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financial.fintechorg.domain.user.User;
import com.financial.fintechorg.dto.UserDTO;
import com.financial.fintechorg.exception.ResourceNotFoundException;
import com.financial.fintechorg.repository.UserRepository;
import com.financial.fintechorg.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User save(UserDTO userDTO) {
        final User user = User.builder()
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .userType(userDTO.getUserType())
                .document(userDTO.getDocument())
                .balance(BigDecimal.ZERO)
                .build();
        return userRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findByUuid(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, id));
    }

    @Override
    public void saveAll(Collection<User> list) {
        userRepository.saveAll(list);
    }

    @Override
    public BigDecimal getBalance(UUID userId) {
        return findById(userId).getBalance();
    }
}
