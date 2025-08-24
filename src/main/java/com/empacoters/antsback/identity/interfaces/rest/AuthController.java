package com.empacoters.antsback.identity.interfaces.rest;

import com.empacoters.antsback.identity.application.usecases.*;
import com.empacoters.antsback.identity.application.usecases.LoginUseCase;
import com.empacoters.antsback.identity.application.usecases.LogoutUseCase;
import com.empacoters.antsback.identity.application.usecases.RefreshTokenUseCase;
import com.empacoters.antsback.identity.application.usecases.RegisterUseCase;
import com.empacoters.antsback.identity.domain.model.User;
import com.empacoters.antsback.identity.interfaces.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;
    private final RequestPasswordResetUseCase requestPasswordResetUseCase;
    private final ConfirmPasswordResetUseCase confirmPasswordResetUseCase;

    public AuthController(
        LoginUseCase loginUseCase, 
        RegisterUseCase registerUseCase, 
        RefreshTokenUseCase refreshTokenUseCase, 
        LogoutUseCase logoutUseCase,
        RequestPasswordResetUseCase requestPasswordResetUseCase,
        ConfirmPasswordResetUseCase confirmPasswordResetUseCase
    ) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.logoutUseCase = logoutUseCase;
        this.requestPasswordResetUseCase = requestPasswordResetUseCase;
        this.confirmPasswordResetUseCase = confirmPasswordResetUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        LoginResponseDTO response = loginUseCase.execute(dto.email(), dto.password());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@AuthenticationPrincipal User userPrincipal, @RequestBody @Valid RegisterRequestDTO dto) {
        var user = registerUseCase.execute(
            dto.name(),
            dto.email(),
            dto.password(),
            dto.role(),
            userPrincipal.roles()
        );

        URI location = URI.create("/users/" + user.id());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestBody @Valid RefreshRequestDTO dto) {
        LoginResponseDTO response = refreshTokenUseCase.execute(dto.refreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid LogoutRequestDTO dto) {
        logoutUseCase.execute(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset/request")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody @Valid PasswordResetRequestDTO dto) {
        requestPasswordResetUseCase.execute(dto.email());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password/reset/confirm")
    public ResponseEntity<Void> confirmPasswordReset(@RequestBody @Valid PasswordResetConfirmDTO dto) {
        confirmPasswordResetUseCase.execute(dto.token(), dto.newPassword());
        return ResponseEntity.ok().build();
    }
}
