package com.getir.job.bstm.auth.rest.controller;

import com.getir.job.bstm.auth.rest.request.LoginRequest;
import com.getir.job.bstm.auth.rest.request.RegisterRequest;
import com.getir.job.bstm.auth.rest.response.RegisterResponse;
import com.getir.job.bstm.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.login(loginRequest));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> customerRegister (@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            authService.registerCustomer(registerRequest);
            return ResponseEntity.ok(new RegisterResponse("User is registered successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> adminRegister (@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            authService.registerAdmin(registerRequest);
            return ResponseEntity.ok(new RegisterResponse("User is registered successfully!"));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
