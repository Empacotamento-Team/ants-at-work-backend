package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.InvalidCredentialsException;
import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.application.services.AuthTokenManager;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.model.UserStatus;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.identity.interfaces.dto.LoginResponseDTO;
import com.empacoters.antsback.shared.vo.Email;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {
    private final UserRepository userRepository;
    private final Hasher hasher;
    private final AuthTokenManager tokenManager;

    public LoginUseCase(
        UserRepository userRepository,
        Hasher hasher,
        AuthTokenManager tokenManager
    ) {
        this.userRepository = userRepository;
        this.hasher = hasher;
        this.tokenManager = tokenManager;
    }

    public LoginResponseDTO execute(String email, String password) {
        User user = userRepository.findByEmail(new Email(email));

        if (user == null || !hasher.matches(password, user.passwordHash()))
            throw new InvalidCredentialsException("Credenciais inválidas! Verifique o endereço de e-mail e a senha.");

        var tokens = tokenManager.generateTokens(user);
        var passwordChangeRequired = user.status().equals(UserStatus.PASSWORD_PENDING);
        return new LoginResponseDTO(passwordChangeRequired, tokens.accessToken(), tokens.refreshToken());
    }
}