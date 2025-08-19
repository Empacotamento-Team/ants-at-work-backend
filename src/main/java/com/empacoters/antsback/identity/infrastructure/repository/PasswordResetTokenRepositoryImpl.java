package com.empacoters.antsback.identity.infrastructure.repository;

import com.empacoters.antsback.identity.domain.model.PasswordResetToken;
import com.empacoters.antsback.identity.domain.repository.PasswordResetTokenRepository;
import com.empacoters.antsback.identity.infrastructure.entity.PasswordResetTokenEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Repository
public class PasswordResetTokenRepositoryImpl implements PasswordResetTokenRepository {
    private final SpringDataPasswordResetTokenJpa springDataPasswordResetTokenJpa;

    public PasswordResetTokenRepositoryImpl(SpringDataPasswordResetTokenJpa springDataPasswordResetTokenJpa) {
        this.springDataPasswordResetTokenJpa = springDataPasswordResetTokenJpa;
    }

    @Override
    public PasswordResetToken save(PasswordResetToken token) {
        var entity = springDataPasswordResetTokenJpa.save(PasswordResetTokenEntity.fromDomain(token));
        return PasswordResetTokenEntity.toDomain(entity);
    }

    @Override
    public Optional<PasswordResetToken> findByTokenHash(String tokenHash) {
        var tokenEntity = springDataPasswordResetTokenJpa.findByTokenHash(tokenHash);
        return tokenEntity.map(PasswordResetTokenEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        springDataPasswordResetTokenJpa.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        springDataPasswordResetTokenJpa.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteExpiredTokens() {
        springDataPasswordResetTokenJpa.deleteByExpirationBefore(Instant.now());
    }
}
