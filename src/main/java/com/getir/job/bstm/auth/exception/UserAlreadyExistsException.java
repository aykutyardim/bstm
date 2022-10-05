package com.getir.job.bstm.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {

    public UserAlreadyExistsException(String msg) {
        super("Credentials is already in use: " + msg);
    }
}
