package com.empacoters.antsback.identity.interfaces.rest;

import com.empacoters.antsback.identity.application.usecases.LoginUseCase;
import com.empacoters.antsback.identity.application.usecases.LogoutUseCase;
import com.empacoters.antsback.identity.application.usecases.RefreshTokenUseCase;
import com.empacoters.antsback.identity.application.usecases.RegisterUseCase;
import com.empacoters.antsback.identity.interfaces.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    public AuthController(LoginUseCase loginUseCase, RegisterUseCase registerUseCase, RefreshTokenUseCase refreshTokenUseCase, LogoutUseCase logoutUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.logoutUseCase = logoutUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        LoginResponseDTO response = loginUseCase.execute(dto.email(), dto.password());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO dto) {
        var user = registerUseCase.execute(dto.name(), dto.email(), dto.password());

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
}
