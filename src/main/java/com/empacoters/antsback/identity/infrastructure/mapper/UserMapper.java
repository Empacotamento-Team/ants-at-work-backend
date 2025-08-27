package com.empacoters.antsback.identity.infrastructure.mapper;

import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.infrastructure.entity.UserEntity;
import com.empacoters.antsback.shared.infrastructure.EmailMapper;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        if (user == null)
            return null;
        return new UserEntity(
                user.id(),
                user.name(),
                EmailMapper.toEmbeddable(user.email()),
                user.passwordHash(),
                user.status(),
                user.roles()
        );
    }

    public static User toDomain(UserEntity userEntity) {
        if (userEntity == null)
            return null;
        return new User(
                userEntity.getId(),
                userEntity.getName(),
                EmailMapper.toDomain(userEntity.getEmail()),
                userEntity.getPasswordHash(),
                userEntity.getStatus(),
                userEntity.getRoles()
        );
    }
}
