package com.empacoters.antsback.identity.application.usecases;

import com.empacoters.antsback.identity.application.exception.UserDoesNotExistException;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.repository.UserRepository;
import com.empacoters.antsback.identity.interfaces.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class GetUserUseCase {
    private final UserRepository userRepository;

    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO execute(Long userId) {
        if (!userRepository.existsById(userId))
            throw new UserDoesNotExistException("Não foi possível encontrar este usuário.");

        User user = userRepository.findById(userId);
        return new UserDTO(user.id(), user.name(), user.email().address());
    }
}
