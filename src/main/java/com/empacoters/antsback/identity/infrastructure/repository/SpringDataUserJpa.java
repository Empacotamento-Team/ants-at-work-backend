package com.empacoters.antsback.identity.infrastructure.repository;

import com.empacoters.antsback.identity.infrastructure.entity.UserEntity;
import com.empacoters.antsback.shared.infrastructure.EmailEmbeddable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface SpringDataUserJpa extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(EmailEmbeddable email);

    @NonNull
    Optional<UserEntity> findById(@NonNull Long id);

    @NonNull
    <T extends UserEntity> T save(@NonNull T entity);

    boolean existsByEmail(EmailEmbeddable email);

    boolean existsById(@NonNull Long id);

    void deleteById(@NonNull Long id);
}
