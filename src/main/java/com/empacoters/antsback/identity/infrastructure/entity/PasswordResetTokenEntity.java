package com.empacoters.antsback.identity.infrastructure.entity;

import com.empacoters.antsback.identity.domain.model.PasswordResetToken;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity(name = "password_reset_tokens")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String tokenHash;

    private Instant expiration;

    public static PasswordResetToken toDomain(PasswordResetTokenEntity entity) {
        if (entity == null) return null;
        return new PasswordResetToken(
            entity.getId(),
            entity.getUserId(),
            entity.getTokenHash(),
            entity.getExpiration()
        );
    }

    public static PasswordResetTokenEntity fromDomain(PasswordResetToken token) {
        if (token == null) return null;
        return new PasswordResetTokenEntity(
            token.id(),
            token.userId(),
            token.tokenHash(),
            token.expiration()
        );
    }
}
