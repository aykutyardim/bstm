package com.getir.job.bstm.user.repository;

import com.getir.job.bstm.user.model.Role;
import com.getir.job.bstm.user.util.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}