package com.getir.job.bstm.auth.service;

import com.getir.job.bstm.auth.rest.request.LoginRequest;
import com.getir.job.bstm.auth.rest.request.RegisterRequest;
import com.getir.job.bstm.auth.rest.response.LoginResponse;
import com.getir.job.bstm.user.model.Role;
import com.getir.job.bstm.user.model.User;

public interface AuthService {

    User register(RegisterRequest registerRequest, Role role);
    User registerAdmin(RegisterRequest registerRequest);
    User registerCustomer(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}
