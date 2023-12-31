package com.financial.fintechorg.exception;

import com.financial.fintechorg.domain.user.User;

public class DuplicatedTransactionException extends RuntimeException {
    public DuplicatedTransactionException(User sender, User receiver) {
        super(String.format("%s -> %s", sender.getFirstName(), receiver.getFirstName()));
    }
}
