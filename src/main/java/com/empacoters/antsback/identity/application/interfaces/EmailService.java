package com.empacoters.antsback.identity.application.interfaces;

public interface EmailService {
    void sendPasswordResetEmail(String email, String resetToken);
}
