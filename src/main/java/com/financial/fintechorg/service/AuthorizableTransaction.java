package com.financial.fintechorg.service;

import java.math.BigDecimal;

import com.financial.fintechorg.domain.user.User;

public interface AuthorizableTransaction {
    boolean authorize(User send, BigDecimal value);
}
