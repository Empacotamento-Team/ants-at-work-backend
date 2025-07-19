package com.empacoters.antsback.identity.interfaces.rest;

import com.empacoters.antsback.identity.application.usecases.DeleteUserUseCase;
import com.empacoters.antsback.identity.application.usecases.GetUserUseCase;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.interfaces.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final GetUserUseCase getUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(
        GetUserUseCase getUserUseCase,
        DeleteUserUseCase deleteUserUseCase
    ) {
        this.getUserUseCase = getUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(@AuthenticationPrincipal User user) {
        var dto = new UserDTO(user.id(), user.name(), user.email().address());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        var response = getUserUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user, @PathVariable Long id) {
        deleteUserUseCase.execute(user, id);
        return ResponseEntity.noContent().build();
    }
}
