package com.example.hrms.service;

import com.example.hrms.entity.RefreshToken;
import com.example.hrms.entity.User;
import com.example.hrms.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60));
        return repository.save(token);
    }

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }

    public void deleteByToken(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        repository.delete(refreshToken);
    }

    public void logoutAll(User user) {
        repository.deleteByUser(user);
    }

}
