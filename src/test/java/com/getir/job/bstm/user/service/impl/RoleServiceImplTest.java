package com.getir.job.bstm.user.service.impl;

import com.getir.job.bstm.user.model.Role;
import com.getir.job.bstm.user.repository.RoleRepository;
import com.getir.job.bstm.user.service.RoleService;
import com.getir.job.bstm.user.util.ERole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

class RoleServiceImplTest {

    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void shouldGetByName() {
        Role role = new Role();
        role.setId(1);
        role.setName(ERole.ROLE_ADMIN);
        doReturn(Optional.of(role)).when(roleRepository).findByName(ERole.ROLE_ADMIN);

        Role expected = role;
        Role actual = roleService.getByName(ERole.ROLE_ADMIN).orElse(null);

        assertNotNull(actual);
        assertEquals(actual, expected);

    }
}