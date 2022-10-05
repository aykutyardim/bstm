package com.getir.job.bstm.auth.service.impl;

import com.getir.job.bstm.auth.exception.RoleNotFoundException;
import com.getir.job.bstm.auth.exception.UserAlreadyExistsException;
import com.getir.job.bstm.auth.model.UserDetailsImpl;
import com.getir.job.bstm.auth.rest.request.LoginRequest;
import com.getir.job.bstm.auth.rest.request.RegisterRequest;
import com.getir.job.bstm.auth.rest.response.LoginResponse;
import com.getir.job.bstm.auth.service.AuthService;
import com.getir.job.bstm.jwt.JwtUtils;
import com.getir.job.bstm.user.model.Role;
import com.getir.job.bstm.user.model.User;
import com.getir.job.bstm.user.service.RoleService;
import com.getir.job.bstm.user.service.UserService;
import com.getir.job.bstm.user.util.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public AuthServiceImpl(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User registerAdmin(RegisterRequest registerRequest) {
        Role role = roleService.getByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RoleNotFoundException("admin"));
        return register(registerRequest, role);
    }

    @Override
    public User registerCustomer(RegisterRequest registerRequest) {
        Role role = roleService.getByName(ERole.ROLE_CUSTOMER)
                .orElseThrow(() -> new RoleNotFoundException("customer"));
        return register(registerRequest, role);
    }
    @Override
    public User register(RegisterRequest registerRequest, Role role){

        if (userService.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("username");
        }

        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("email");
        }

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);

        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                userRoles);

        return userService.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        return new LoginResponse(
                jwt,
                "Bearer ",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
}
