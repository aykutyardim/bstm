package com.getir.job.bstm.user.service.impl;

import com.getir.job.bstm.user.model.Role;
import com.getir.job.bstm.user.repository.RoleRepository;
import com.getir.job.bstm.user.service.RoleService;
import com.getir.job.bstm.user.util.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {


    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> getByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
