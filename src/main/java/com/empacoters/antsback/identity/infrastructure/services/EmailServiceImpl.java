package com.empacoters.antsback.identity.infrastructure.services;

import com.empacoters.antsback.identity.application.interfaces.EmailService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendPasswordResetEmail(String email, String resetToken) {
        // Por enquanto, apenas loga o token (em produção, enviaria email real)
        logger.info("=== EMAIL DE REDEFINIÇÃO DE SENHA ===");
        logger.info("Destinatário: {}", email);
        logger.info("Token de redefinição: {}", resetToken);
        logger.info("Link de redefinição: http://localhost:3000/reset-password?token={}", resetToken);
        logger.info("O token expira em 1 hora.");
        logger.info("=====================================");
        
        // TODO: Implementar envio real do email
        // Exemplo: usando JavaMailSender, SendGrid, etc.
    }
}
