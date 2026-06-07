package com.example.networkbackend.service;

import com.example.networkbackend.entity.User;
import com.example.networkbackend.repository.AuthRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(User user) {
        // Check if username already exists
        if (authRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username is already taken!";
        }

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authRepository.save(user);
        return "User registered successfully!";
    }

    public String loginUser(String username, String password) {
        Optional<User> userOpt = authRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Check if the raw password matches the hashed password in DB
            if (passwordEncoder.matches(password, user.getPassword())) {
                return "Login successful!";
            }
        }
        return "Invalid username or password.";
    }
}
