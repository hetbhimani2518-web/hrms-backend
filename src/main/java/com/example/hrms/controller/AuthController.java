package com.example.hrms.controller;

import com.example.hrms.common.ApiResponse;
import com.example.hrms.dto.request.LoginRequestDTO;
import com.example.hrms.dto.request.LogoutRequestDTO;
import com.example.hrms.dto.response.LoginResponseDTO;
import com.example.hrms.entity.User;
import com.example.hrms.repository.UserRepository;
import com.example.hrms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        LoginResponseDTO response = authService.login(request, user);

        return ResponseEntity.ok(
                ApiResponse.<LoginResponseDTO>builder()
                        .success(true)
                        .message("Login successful")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}