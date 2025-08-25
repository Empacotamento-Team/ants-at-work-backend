package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.model.UserStatus;
import com.empacoters.antsback.identity.domain.repository.RefreshTokenRepository;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.shared.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class FirstAccessPasswordChangeUseCase {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final Hasher hasher;

    public FirstAccessPasswordChangeUseCase(
        RefreshTokenRepository refreshTokenRepository,
        UserRepository userRepository,
        Hasher hasher
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    public void execute(User user, String newPassword) {
        if (!user.status().equals(UserStatus.PASSWORD_PENDING))
            throw new BadRequestException("Somente é permitido alterar a senha dessa forma em primeiros acessos!");

        var passwordHash = hasher.hash(newPassword);
        user.changePasswordHash(passwordHash);
        user.changeStatus(UserStatus.ACTIVE);

        userRepository.save(user);
        refreshTokenRepository.deleteByUserId(user.id());  // Removing all refreshTokens for this user
    }
}
