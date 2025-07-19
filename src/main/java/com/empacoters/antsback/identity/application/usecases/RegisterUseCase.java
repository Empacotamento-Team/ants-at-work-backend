package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.EmailAlreadyRegisteredException;
import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.model.UserRole;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.shared.vo.Email;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class RegisterUseCase {
    private final UserRepository userRepository;
    private final Hasher hasher;

    public RegisterUseCase(UserRepository userRepository, Hasher hasher) {
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    public User execute(String name, String email, String password) {
        var emailObj = new Email(email);
        if (userRepository.existsByEmail(emailObj))
            throw new EmailAlreadyRegisteredException("E-mail já cadastrado!");

        var passwordHash = hasher.hash(password);
        var roles = new HashSet<>(List.of(UserRole.ADMIN));
        var user = new User(null, name, emailObj, passwordHash, roles);
        return userRepository.save(user);
    }
}
