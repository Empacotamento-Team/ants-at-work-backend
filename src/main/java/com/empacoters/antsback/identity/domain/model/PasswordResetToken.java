package com.empacoters.antsback.identity.domain.model;

import java.time.Instant;

public class PasswordResetToken {
    private final Long id;
    private final Long userId;
    private final String tokenHash;
    private final Instant expiration;

    public PasswordResetToken(Long id, Long userId, String tokenHash, Instant expiration) {
        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiration = expiration;
    }

    public Long id() {
        return id;
    }

    public Long userId() {
        return userId;
    }

    public String tokenHash() {
        return tokenHash;
    }

    public Instant expiration() {
        return expiration;
    }

    public boolean isExpired() {
        return expiration.isBefore(Instant.now());
    }
}
