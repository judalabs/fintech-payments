package com.financial.fintechorg.exception;

public class TransactionAuthorizationException extends RuntimeException {
    public TransactionAuthorizationException(String message) {
        super(message);
    }
}
