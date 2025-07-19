package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.ExpiredTokenException;
import com.empacoters.antsback.identity.application.exception.InvalidTokenException;
import com.empacoters.antsback.identity.application.exception.UserDoesNotExistException;
import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.application.services.AuthTokenManager;
import com.empacoters.antsback.identity.domain.repository.RefreshTokenRepository;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.identity.interfaces.dto.LoginResponseDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenUseCase {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final Hasher hasher;
    private final AuthTokenManager tokenManager;

    public RefreshTokenUseCase(
        RefreshTokenRepository refreshTokenRepository,
        UserRepository userRepository,
        Hasher hasher,
        AuthTokenManager tokenManager
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.hasher = hasher;
        this.tokenManager = tokenManager;
    }

    public LoginResponseDTO execute(String refreshToken) {
        var hashedToken = hasher.hashToken(refreshToken);
        var token = refreshTokenRepository.findByTokenHash(hashedToken)
            .orElseThrow(() -> new InvalidTokenException("Refresh token inválido!"));

        if (token.expiration().isBefore(Instant.now()))
            throw new ExpiredTokenException("Refresh token expirado!");

        var user = userRepository.findById(token.userId());
        if (user == null)
            throw new UserDoesNotExistException("Usuário não encontrado");

        refreshTokenRepository.deleteById(token.id());
        var newTokens = tokenManager.generateTokens(user);
        return new LoginResponseDTO(newTokens.accessToken(), newTokens.refreshToken());
    }
}
