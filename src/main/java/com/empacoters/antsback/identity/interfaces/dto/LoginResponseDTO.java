package com.empacoters.antsback.identity.interfaces.dto;

public record LoginResponseDTO(boolean passwordChangeRequired, String accessToken, String refreshToken) { }
