package com.financial.fintechorg.exception;

public class DeniedAuthorizationException extends RuntimeException {
    public DeniedAuthorizationException(String idPlusAmound) {
        super(idPlusAmound);
    }
}
