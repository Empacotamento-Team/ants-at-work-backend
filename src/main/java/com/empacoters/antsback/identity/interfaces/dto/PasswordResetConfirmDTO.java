package com.empacoters.antsback.identity.interfaces.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PasswordResetConfirmDTO(
    @NotEmpty String token,
    @NotEmpty @Size(min = 8) String newPassword
) { }
