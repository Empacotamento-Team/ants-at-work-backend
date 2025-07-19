package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.InvalidCredentialsException;
import com.empacoters.antsback.identity.application.exception.UserDoesNotExistException;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCase {
    private final UserRepository userRepository;

    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(User contextUser, Long userId) {
        if (!userRepository.existsById(userId))
            throw new UserDoesNotExistException("Não é possível apagar um usuário que não existe!");
        if (!contextUser.id().equals(userId))
            throw new InvalidCredentialsException("Você não tem permissão para apagar esse usuário!");

        userRepository.deleteById(userId);
    }
}
