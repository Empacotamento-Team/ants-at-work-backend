package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.EmailAlreadyRegisteredException;
import com.empacoters.antsback.identity.application.interfaces.Hasher;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.model.UserRole;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.shared.exception.BadRequestException;
import com.empacoters.antsback.shared.vo.Email;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegisterUseCase {
    private final UserRepository userRepository;
    private final Hasher hasher;

    public RegisterUseCase(UserRepository userRepository, Hasher hasher) {
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    public User execute(String name, String email, String password, String role, Set<UserRole> requesterRoles) {
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

        var passwordHash = hasher.hash(password);

        Set<UserRole> roles = new HashSet<>();
        roles.add(newUserRoleLevel);
        if (newUserRoleIsAdmin) {
            // Adding Manager Role so Admins have the same permissions
            roles.add(UserRole.MANAGER);
        }

        var user = new User(null, name, emailObj, passwordHash, roles);
        return userRepository.save(user);
    }
}
