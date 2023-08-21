package com.financial.fintechorg.service;

import com.financial.fintechorg.domain.user.User;

public interface NotifiableTransaction {
    void sendNotification(User user, String message);
}
