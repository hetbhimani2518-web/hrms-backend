package com.example.hrms.service;

import com.example.hrms.dto.request.LoginRequestDTO;
import com.example.hrms.dto.response.LoginResponseDTO;
import com.example.hrms.entity.User;
import com.example.hrms.security.JwtService;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public LoginResponseDTO login(LoginRequestDTO request, User user) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}