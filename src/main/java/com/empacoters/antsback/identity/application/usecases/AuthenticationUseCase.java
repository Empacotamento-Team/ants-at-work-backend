package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.ExpiredTokenException;
import com.empacoters.antsback.identity.application.exception.InvalidTokenException;
import com.empacoters.antsback.identity.application.interfaces.TokenService;
import com.empacoters.antsback.identity.application.interfaces.TokenStatus;
import com.empacoters.antsback.identity.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUseCase {
    private final TokenService tokenService;

    public AuthenticationUseCase(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public User authenticateToken(String token) {
        var tokenStatus = tokenService.validateToken(token);
        if (tokenStatus == TokenStatus.EXPIRED)
            throw new ExpiredTokenException("Token expirado!");
        if (tokenStatus == TokenStatus.INVALID)
            throw new InvalidTokenException("Token inválido!");

        return tokenService.extractSubject(token);
    }
}
