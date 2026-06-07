package com.example.networkbackend.controller;

import com.example.networkbackend.entity.User;
import com.example.networkbackend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String result = authService.registerUser(user);
        if (result.contains("successfully")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        String result = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (result.contains("successful")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(401).body(result);
    }
}
