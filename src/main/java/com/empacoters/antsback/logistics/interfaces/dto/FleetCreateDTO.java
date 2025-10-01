package com.empacoters.antsback.logistics.interfaces.dto;

public record FleetCreateDTO(
        String name,
        String code,
        String placeOfOperation,
        Long[] trucksIds
) {}
