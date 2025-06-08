package com.pet.social.auth.service;

import com.pet.social.auth.dto.AuthRequest;
import com.pet.social.auth.dto.AuthResponse;
import com.pet.social.auth.dto.RegisterRequest;
import com.pet.social.auth.model.User;
import com.pet.social.auth.repository.UserRepository;
import com.pet.social.auth.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            return new AuthResponse(null, "Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, "Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            String token = jwtUtil.generateToken(authRequest.getUsername());
            return new AuthResponse(token);
        } catch (Exception e) {
            return new AuthResponse(null, "Invalid username or password");
        }
    }
}