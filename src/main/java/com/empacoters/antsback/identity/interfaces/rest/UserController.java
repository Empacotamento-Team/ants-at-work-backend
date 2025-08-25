package com.empacoters.antsback.identity.interfaces.rest;

import com.empacoters.antsback.identity.application.usecases.DeleteUserUseCase;
import com.empacoters.antsback.identity.application.usecases.GetUserUseCase;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.domain.model.UserRole;
import com.empacoters.antsback.identity.interfaces.dto.UserDTO;
import com.empacoters.antsback.identity.interfaces.dto.UserRoleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        var dto = new UserDTO(user.id(), user.name(), user.email().address(), user.roles().toArray(new UserRole[0]));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        var response = getUserUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/roles")
    public ResponseEntity<UserRoleDTO[]> getUserRoles(@AuthenticationPrincipal User user) {
        List<UserRoleDTO> rolesList = new ArrayList<>();
        var roles = UserRole.values();

        for (var role : roles) {
            boolean canRegister = user.roles().contains(UserRole.ADMIN)
                || (user.roles().contains(UserRole.MANAGER) && !role.equals(UserRole.ADMIN));

            var dto = new UserRoleDTO(role.description(), canRegister);
            rolesList.add(dto);
        }
        return ResponseEntity.ok(rolesList.toArray(new UserRoleDTO[0]));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user, @PathVariable Long id) {
        deleteUserUseCase.execute(user, id);
        return ResponseEntity.noContent().build();
    }
}
