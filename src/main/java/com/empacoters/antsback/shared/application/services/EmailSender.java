package com.empacoters.antsback.shared.application.services;

import com.empacoters.antsback.shared.vo.Email;

public interface EmailSender {
    void send(Email[] recipients, String subject, String bodyHtml);
}
