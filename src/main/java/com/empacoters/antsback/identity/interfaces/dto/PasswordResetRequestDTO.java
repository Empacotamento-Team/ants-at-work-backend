package com.empacoters.antsback.identity.interfaces.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record PasswordResetRequestDTO(@NotEmpty @Email String email) { }
