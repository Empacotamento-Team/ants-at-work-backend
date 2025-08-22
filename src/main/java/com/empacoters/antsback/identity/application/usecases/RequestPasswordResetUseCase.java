package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.UserDoesNotExistException;
import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.domain.model.PasswordResetToken;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.repository.PasswordResetTokenRepository;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.identity.infrastructure.security.JwtTokenService;
import com.empacoters.antsback.shared.application.services.EmailSender;
import com.empacoters.antsback.shared.application.services.EmailTemplateRenderer;
import com.empacoters.antsback.shared.vo.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class RequestPasswordResetUseCase {
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailSender emailSender;
    private final EmailTemplateRenderer emailTemplateRenderer;
    private final Hasher hasher;
    private final JwtTokenService jwtTokenService;

    @Value("${frontend.url}")
    private String frontendUrl;

    public RequestPasswordResetUseCase(
        UserRepository userRepository,
        PasswordResetTokenRepository passwordResetTokenRepository,
        EmailSender emailSender,
        EmailTemplateRenderer emailTemplateRenderer,
        Hasher hasher,
        JwtTokenService jwtTokenService
    ) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailSender = emailSender;
        this.emailTemplateRenderer =  emailTemplateRenderer;
        this.hasher = hasher;
        this.jwtTokenService = jwtTokenService;
    }

    public void execute(String email) {
        User user = userRepository.findByEmail(new Email(email));
        if (user == null) {
            throw new UserDoesNotExistException("Não há nenhum usuário cadastrado com o e-mail informado");
        }

        passwordResetTokenRepository.deleteByUserId(user.id());

        String token = jwtTokenService.generatePasswordResetToken(32);
        String tokenHash = hasher.hashToken(token);
        Instant expiration = Instant.now().plus(1, ChronoUnit.HOURS); // 1 hora de validade

        PasswordResetToken resetToken = new PasswordResetToken(
            null, 
            user.id(), 
            tokenHash, 
            expiration
        );
        
        passwordResetTokenRepository.save(resetToken);
        this.sendPasswordResetEmail(user, token);
    }

    private void sendPasswordResetEmail(User user, String token) {
        var destinatarios = new Email[] {user.email()};
        var assunto = "Redefinição de Senha!";
        var modelo = "password-reset";
        String link = UriComponentsBuilder.fromUriString(frontendUrl + "/reset-password")
                .queryParam("token", token)
                .toUriString();

        Map<String, Object> dados = new HashMap<>();
        dados.put("usuario", user.name().split(" ")[0]);
        dados.put("link" , link);

        var mensagem = this.emailTemplateRenderer.render(modelo, dados);
        this.emailSender.send(destinatarios, assunto, mensagem);
    }
}

