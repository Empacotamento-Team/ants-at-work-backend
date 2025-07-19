package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.InvalidTokenException;
import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.domain.repository.RefreshTokenRepository;
import com.empacoters.antsback.identity.interfaces.dto.LogoutRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCase {
    private final RefreshTokenRepository refreshTokenRepository;
    private final Hasher hasher;

    public LogoutUseCase(RefreshTokenRepository refreshTokenRepository, Hasher hasher) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.hasher = hasher;
    }

    public void execute(LogoutRequestDTO dto) {
        var hashedToken = hasher.hashToken(dto.refreshToken());
        var dbToken = refreshTokenRepository.findByTokenHash(hashedToken)
            .orElseThrow(() -> new InvalidTokenException("Não é possível fazer logout com este token."));

        refreshTokenRepository.deleteById(dbToken.id());
    }
}
