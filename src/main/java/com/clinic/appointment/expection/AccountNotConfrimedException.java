package com.clinic.appointment.expection;

import org.springframework.security.core.AuthenticationException;

public class AccountNotConfrimedException extends AuthenticationException {
    public AccountNotConfrimedException(String message) {
        super(message);
    }
}
