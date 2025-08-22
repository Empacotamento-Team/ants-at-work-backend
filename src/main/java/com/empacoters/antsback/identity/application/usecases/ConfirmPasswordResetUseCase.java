package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.ExpiredPasswordResetTokenException;
import com.empacoters.antsback.identity.application.exception.InvalidPasswordResetTokenException;
import com.empacoters.antsback.identity.application.exception.UserDoesNotExistException;
import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.repository.PasswordResetTokenRepository;
import com.empacoters.antsback.identity.domain.repository.RefreshTokenRepository;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ConfirmPasswordResetUseCase {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Hasher hasher;

    public ConfirmPasswordResetUseCase(
        PasswordResetTokenRepository passwordResetTokenRepository,
        UserRepository userRepository,
        RefreshTokenRepository refreshTokenRepository,
        Hasher hasher
    ) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.hasher = hasher;
    }
    @Transactional
    public void execute(String token, String newPassword) {
        String tokenHash = hasher.hashToken(token);
        
        var resetToken = passwordResetTokenRepository.findByTokenHash(tokenHash)
            .orElseThrow(() -> new InvalidPasswordResetTokenException("Token de redefinição inválido!"));

        if (resetToken.isExpired()) {
            passwordResetTokenRepository.deleteById(resetToken.id());
            throw new ExpiredPasswordResetTokenException("Token de redefinição expirado!");
        }

        User user = userRepository.findById(resetToken.userId());
        if (user == null) {
            throw new UserDoesNotExistException("Usuário não encontrado!");
        }

        // Atualiza a senha
        String newPasswordHash = hasher.hash(newPassword);
        user.changePasswordHash(newPasswordHash);
        userRepository.save(user);

        // Remove o token usado
        passwordResetTokenRepository.deleteById(resetToken.id());
        
        // Remove todos os refresh tokens do usuário (força novo login)
        refreshTokenRepository.deleteByUserId(user.id());
    }
}
