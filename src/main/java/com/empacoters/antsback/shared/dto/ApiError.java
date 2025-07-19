package com.empacoters.antsback.shared.dto;

public record ApiError(
    Integer status,
    String error,
    String name,
    String message
) {}
