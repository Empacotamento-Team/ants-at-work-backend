package com.empacoters.antsback.identity.infrastructure.repository;

import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.identity.infrastructure.mapper.UserMapper;
import com.empacoters.antsback.shared.infrastructure.EmailMapper;
import com.empacoters.antsback.shared.vo.Email;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SpringDataUserJpa springDataUserJpa;

    public UserRepositoryImpl(SpringDataUserJpa springDataUserJpa) {
        this.springDataUserJpa = springDataUserJpa;
    }

    @Override
    public User findByEmail(Email email) {
        var entity = springDataUserJpa.findByEmail(EmailMapper.toEmbeddable(email)).orElse(null);
        return UserMapper.toDomain(entity);
    }

    @Override
    public User findById(Long id) {
        var entity = springDataUserJpa.findById(id).orElse(null);
        return UserMapper.toDomain(entity);
    }

    @Override
    @NonNull
    public User save(@NonNull User user) {
        var savedUser = springDataUserJpa.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(savedUser);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return springDataUserJpa.existsByEmail(EmailMapper.toEmbeddable(email));
    }

    @Override
    public boolean existsById(Long id) {
        return springDataUserJpa.existsById(id);
    }

    @Override
    public void deleteById(@NonNull Long id) {
        springDataUserJpa.deleteById(id);
    }
}
