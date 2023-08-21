package com.financial.fintechorg.exception;

import java.util.UUID;

import com.financial.fintechorg.domain.user.User;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<User> clss, UUID id) {
        super(String.format("ID :%s (%s)", id, clss.getCanonicalName()));
    }
}
