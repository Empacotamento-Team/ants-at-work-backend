package com.empacoters.antsback.shared.infrastructure;

import com.empacoters.antsback.shared.application.services.EmailSender;
import com.empacoters.antsback.shared.vo.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EmailSenderImpl implements EmailSender {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(Email[] recipients, String subject, String bodyHtml) {
        String[] recipientsAddresses = Arrays.stream(recipients).map(Email::toString).toArray(String[]::new);

        var message = javaMailSender.createMimeMessage();
        try {
            var helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(recipientsAddresses);
            helper.setSubject(subject);
            helper.setText(bodyHtml, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
