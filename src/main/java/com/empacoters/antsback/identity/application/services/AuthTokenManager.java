package com.empacoters.antsback.identity.application.services;

import com.empacoters.antsback.identity.application.dto.TokenBundle;
import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.application.interfaces.TokenService;
import com.empacoters.antsback.identity.domain.model.RefreshToken;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuthTokenManager {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenService tokenService;
    private final Hasher hasher;

    public AuthTokenManager(
        RefreshTokenRepository refreshTokenRepository,
        TokenService tokenService,
        Hasher hasher
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenService = tokenService;
        this.hasher = hasher;
    }

    public TokenBundle generateTokens(User user) {
        Instant accessExpiration = Instant.now().plus(20, ChronoUnit.MINUTES);
        Instant refreshExpiration = Instant.now().plus(4, ChronoUnit.DAYS);

        var accessToken = tokenService.generateAccessToken(user, accessExpiration);
        var refreshToken = tokenService.generateRefreshToken(user, refreshExpiration);

        var hashedRefreshToken = hasher.hashToken(refreshToken);
        RefreshToken refreshTokenObj = new RefreshToken(null, user.id(), hashedRefreshToken, refreshExpiration);
        refreshTokenRepository.save(refreshTokenObj);

        return new TokenBundle(accessToken, refreshToken);
    }
}
