package com.empacoters.antsback.identity.interfaces.dto;

import jakarta.validation.constraints.NotEmpty;

public record LogoutRequestDTO(@NotEmpty String refreshToken) { }
