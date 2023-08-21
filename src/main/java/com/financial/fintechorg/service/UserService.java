package com.financial.fintechorg.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

import com.financial.fintechorg.domain.user.User;
import com.financial.fintechorg.dto.UserDTO;

public interface UserService {

    User save(UserDTO userDTO);

    User findById(UUID id);

    void saveAll(Collection<User> list);

    BigDecimal getBalance(UUID userId);
}
