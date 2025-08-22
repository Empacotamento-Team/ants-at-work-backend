package com.empacoters.antsback.identity.infrastructure.repository;

import com.empacoters.antsback.identity.infrastructure.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Optional;

public interface SpringDataPasswordResetTokenJpa extends JpaRepository<PasswordResetTokenEntity, Long> {
    Optional<PasswordResetTokenEntity> findByTokenHash(String tokenHash);

    void deleteByUserId(Long userId);

    @Modifying
    @Query("DELETE FROM password_reset_tokens p WHERE p.expiration < :now")
    void deleteByExpirationBefore(Instant now);

    @NonNull
    <T extends PasswordResetTokenEntity> T save(@NonNull T entity);

    void deleteById(@NonNull Long id);
}
