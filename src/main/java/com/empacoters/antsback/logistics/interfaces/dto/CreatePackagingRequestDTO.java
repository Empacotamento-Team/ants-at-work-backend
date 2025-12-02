package com.empacoters.antsback.logistics.interfaces.dto;

public record CreatePackagingRequestDTO(
    String name,
    String description,
    Double height,
    Double width,
    Double length
) {}
