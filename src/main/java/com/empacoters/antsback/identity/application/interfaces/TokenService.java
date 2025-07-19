package com.empacoters.antsback.identity.application.interfaces;

import com.empacoters.antsback.identity.domain.model.User;

import java.time.Instant;

public interface TokenService {
    String generateAccessToken(User user, Instant expiration);
    String generateRefreshToken(User user, Instant expiration);
    TokenStatus validateToken(String token);
    User extractSubject(String token);
}
