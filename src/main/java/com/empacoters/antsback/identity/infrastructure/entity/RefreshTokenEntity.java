package com.empacoters.antsback.identity.infrastructure.entity;

import com.empacoters.antsback.identity.domain.model.RefreshToken;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity(name = "refresh_tokens")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String tokenHash;

    private Instant expiration;

    public static RefreshToken toDomain(RefreshTokenEntity entity) {
        return new RefreshToken(entity.getId(), entity.getUserId(), entity.getTokenHash(), entity.getExpiration());
    }
}
