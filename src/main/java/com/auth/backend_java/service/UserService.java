package com.auth.backend_java.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.backend_java.repository.UserRepository;
import com.auth.backend_java.util.JwtUtil;
import com.auth.backend_java.model.*;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Constructor for dependency injection
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Register a user and hash the password before saving
    public Userdata registerUser(Userdata user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    // Login user, check credentials and return JWT token
    public String loginUser(String username, String password) {
    // Retrieve user by username
    Optional<Userdata> userOptional = userRepository.findByUsername(username);

    // Check if user exists and password matches
    if (userOptional.isPresent()) {
        Userdata user = userOptional.get();

        if (passwordEncoder.matches(password, user.getPassword())) {
            // Retrieve the role from the user object
            Role role = user.getRole(); // Assuming 'getRole()' returns the role of the user

            // Generate and return JWT token with username and role
            return jwtUtil.generateToken(username, role);
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    } else {
        throw new BadCredentialsException("Invalid username");
    }
}

    public Userdata findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
