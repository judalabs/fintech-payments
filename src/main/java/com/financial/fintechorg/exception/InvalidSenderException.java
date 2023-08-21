package com.financial.fintechorg.exception;

import java.util.UUID;

public class InvalidSenderException extends RuntimeException {
    public InvalidSenderException(UUID id) {
        super(String.valueOf(id));
    }
}
