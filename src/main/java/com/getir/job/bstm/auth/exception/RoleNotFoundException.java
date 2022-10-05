package com.getir.job.bstm.auth.exception;

import javax.persistence.EntityNotFoundException;

public class RoleNotFoundException extends EntityNotFoundException {

    public RoleNotFoundException(String role) {
        super("Role is not found: " + role);
    }
}
