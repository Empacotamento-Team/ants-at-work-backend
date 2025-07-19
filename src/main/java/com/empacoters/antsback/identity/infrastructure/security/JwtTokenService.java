package com.empacoters.antsback.identity.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.empacoters.antsback.identity.application.interfaces.TokenService;
import com.empacoters.antsback.identity.application.interfaces.TokenStatus;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.shared.vo.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtTokenService implements TokenService {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final UserRepository userRepository;

    public JwtTokenService(
        @Value("${security.token.secret") String secret,
        UserRepository userRepository
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).build();
        this.userRepository = userRepository;
    }

    @Override
    public String generateAccessToken(User user, Instant expiration) {
        try {
            return JWT.create()
                    .withIssuer("auth-generate-token")
                    .withSubject(user.email().address())
                    .withExpiresAt(expiration)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token.");
        }
    }

    @Override
    public String generateRefreshToken(User user, Instant expiration) {
        try {
            return JWT.create()
                    .withIssuer("auth-generate-refresh-token")
                    .withSubject(user.email().address())
                    .withExpiresAt(expiration)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar refresh token.");
        }
    }

    @Override
    public TokenStatus validateToken(String token) {
        try {
            verifier.verify(token);
            return TokenStatus.VALID;
        } catch (TokenExpiredException e) {
            return TokenStatus.EXPIRED;
        } catch (JWTVerificationException e) {
            return TokenStatus.INVALID;
        }
    }

    @Override
    public User extractSubject(String token) {
        try {
            var jwt = verifier.verify(token);
            var userEmail = new Email(jwt.getSubject());
            return userRepository.findByEmail(userEmail);
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
