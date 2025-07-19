package com.empacoters.antsback.identity.infrastructure.repository;

import com.empacoters.antsback.identity.infrastructure.entity.RefreshTokenEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataRefreshTokenJpa extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByTokenHash(String token);

    void deleteByUserId(Long userId);

    @Nonnull
    <S extends RefreshTokenEntity> S save(@Nonnull S refreshTokenEntity);

    void deleteById(@Nonnull Long id);
}
