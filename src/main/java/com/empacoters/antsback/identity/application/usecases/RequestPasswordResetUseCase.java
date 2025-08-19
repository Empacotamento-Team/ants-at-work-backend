package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.interfaces.EmailService;
import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.domain.model.PasswordResetToken;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.repository.PasswordResetTokenRepository;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.identity.infrastructure.security.JwtTokenService;
import com.empacoters.antsback.shared.vo.Email;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class RequestPasswordResetUseCase {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;
    private final Hasher hasher;
    private final JwtTokenService jwtTokenService;

    public RequestPasswordResetUseCase(
        UserRepository userRepository,
        PasswordResetTokenRepository passwordResetTokenRepository,
        EmailService emailService,
        Hasher hasher,
        JwtTokenService jwtTokenService
    ) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
        this.hasher = hasher;
        this.jwtTokenService = jwtTokenService;
    }

    public void execute(String email) {
        User user = userRepository.findByEmail(new Email(email));
        
        // Se o usuário não existir, não fazemos nada (por segurança)
        if (user == null) {
            return;
        }

        // Remove tokens anteriores do usuário
        passwordResetTokenRepository.deleteByUserId(user.id());

        // Gera novo token JWT para redefinição de senha
        String token = jwtTokenService.generatePasswordResetToken(user);
        String tokenHash = hasher.hashToken(token);
        Instant expiration = Instant.now().plus(1, ChronoUnit.HOURS); // 1 hora de validade

        PasswordResetToken resetToken = new PasswordResetToken(
            null, 
            user.id(), 
            tokenHash, 
            expiration
        );
        
        passwordResetTokenRepository.save(resetToken);
        emailService.sendPasswordResetEmail(email, token);
    }
}
