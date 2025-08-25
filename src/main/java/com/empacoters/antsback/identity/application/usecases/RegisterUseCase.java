package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.EmailAlreadyRegisteredException;
import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.model.UserRole;
import com.empacoters.antsback.identity.domain.model.UserStatus;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.shared.application.services.EmailSender;
import com.empacoters.antsback.shared.application.services.EmailTemplateRenderer;
import com.empacoters.antsback.shared.exception.BadRequestException;
import com.empacoters.antsback.shared.vo.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class RegisterUseCase {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxy";
    private static final String DIGITS = "0123456789";
    private static final String ALL_CHARS = UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGITS;

    private final EmailSender emailSender;
    private final EmailTemplateRenderer emailTemplateRenderer;
    private final UserRepository userRepository;
    private final Hasher hasher;

    @Value("${frontend.url}")
    private String frontendUrl;

    public RegisterUseCase(
        EmailSender emailSender,
        EmailTemplateRenderer emailTemplateRenderer,
        UserRepository userRepository,
        Hasher hasher
    ) {
        this.emailSender = emailSender;
        this.emailTemplateRenderer = emailTemplateRenderer;
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    @Transactional
    public User execute(String name, String email, String role, Set<UserRole> requesterRoles) {
        UserRole newUserRoleLevel = UserRole.fromDescription(role)
            .orElseThrow(() -> new BadRequestException("Cargo inválido!"));
        boolean newUserRoleIsAdmin = newUserRoleLevel.equals(UserRole.ADMIN);

        if (newUserRoleIsAdmin && !requesterRoles.contains(UserRole.ADMIN)) {
            throw new AccessDeniedException("Somente administradores podem cadastrar novos administradores!");
        }
        if (!requesterRoles.contains(UserRole.MANAGER)) {
            throw new AccessDeniedException("Somente gestores podem cadastrar novos usuários!");
        }

        var emailObj = new Email(email);
        if (userRepository.existsByEmail(emailObj))
            throw new EmailAlreadyRegisteredException("E-mail já cadastrado!");

        var password = this.generateRandomPassword();
        var passwordHash = hasher.hash(password);

        Set<UserRole> roles = new HashSet<>();
        roles.add(newUserRoleLevel);
        if (newUserRoleIsAdmin) {
            // Adding Manager Role so Admins have the same permissions
            roles.add(UserRole.MANAGER);
        }

        var user = userRepository.save(
            new User(null, name, emailObj, passwordHash, UserStatus.PASSWORD_PENDING, roles)
        );
        this.sendFirstAccessPasswordEmail(user.name(), user.email(), password);
        return user;
    }

    private void sendFirstAccessPasswordEmail(String userName, Email emailAddress, String password) {
        var recipients = new Email[] { emailAddress };
        var subject = "Senha de Primeiro Acesso";
        var template = "first-access";

        Map<String, Object> dados = new HashMap<>();
        dados.put("usuario", userName.split(" ")[0]);
        dados.put("link", frontendUrl + "/login");
        dados.put("senha", password);

        var mensagem = this.emailTemplateRenderer.render(template, dados);
        this.emailSender.send(recipients, subject, mensagem);
    }

    private String generateRandomPassword() {
        int length = 12;
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int charIndex = secureRandom.nextInt(ALL_CHARS.length());
            char randomChar = ALL_CHARS.charAt(charIndex);

            passwordBuilder.append(randomChar);
        }

        return passwordBuilder.toString();
    }
}
