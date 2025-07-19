package com.empacoters.antsback.identity.infrastructure.repository;

import com.empacoters.antsback.identity.domain.model.RefreshToken;
import com.empacoters.antsback.identity.domain.repository.RefreshTokenRepository;
import com.empacoters.antsback.identity.infrastructure.entity.RefreshTokenEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final SpringDataRefreshTokenJpa springDataRefreshTokenJpa;

    public RefreshTokenRepositoryImpl(SpringDataRefreshTokenJpa springDataRefreshTokenJpa) {
        this.springDataRefreshTokenJpa = springDataRefreshTokenJpa;
    }

    @Override
    public Optional<RefreshToken> findByTokenHash(String token) {
        var tokenEntity = springDataRefreshTokenJpa.findByTokenHash(token);
        return tokenEntity.map(RefreshTokenEntity::toDomain);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        var entity = springDataRefreshTokenJpa.save(new RefreshTokenEntity(
            refreshToken.id(),
            refreshToken.userId(),
            refreshToken.tokenHash(),
            refreshToken.expiration()
        ));
        return RefreshTokenEntity.toDomain(entity);
    }

    @Override
    public void deleteById(Long id) {
        springDataRefreshTokenJpa.deleteById(id);
    }

    @Override
    public void deleteByUserId(Long id) {
        springDataRefreshTokenJpa.deleteByUserId(id);
    }
}
