package com.empacoters.antsback.identity.domain.repository;

import com.empacoters.antsback.identity.domain.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository {
    PasswordResetToken save(PasswordResetToken token);
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);
    void deleteById(Long id);
    void deleteByUserId(Long userId);
    void deleteExpiredTokens();
}
