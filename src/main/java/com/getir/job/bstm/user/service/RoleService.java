package com.getir.job.bstm.user.service;

import com.getir.job.bstm.user.model.Role;
import com.getir.job.bstm.user.util.ERole;

import java.util.Optional;

public interface RoleService {

    Optional<Role> getByName(ERole name);
}
