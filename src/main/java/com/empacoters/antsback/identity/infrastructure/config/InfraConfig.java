package com.empacoters.antsback.identity.infrastructure.config;

import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.infrastructure.security.HasherImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InfraConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Hasher hasher(PasswordEncoder encoder) {
        return new HasherImpl(encoder);
    }
}
