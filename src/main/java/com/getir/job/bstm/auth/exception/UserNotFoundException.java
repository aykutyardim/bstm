package com.getir.job.bstm.auth.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends UsernameNotFoundException {

    public UserNotFoundException(String username) {
        super("User is not found with username: " + username);
    }
}
