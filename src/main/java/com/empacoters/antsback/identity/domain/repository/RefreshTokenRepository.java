package com.empacoters.antsback.identity.domain.repository;

import com.empacoters.antsback.identity.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findByTokenHash(String token);

    RefreshToken save(RefreshToken refreshToken);

    void deleteById(Long id);

    void deleteByUserId(Long id);
}
