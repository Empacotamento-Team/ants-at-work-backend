package com.empacoters.antsback.logistics.interfaces.dto;

public record FleetUpdateDTO (
        Long id,
        String name,
        String code,
        String placeOfOperation,
        Long[] trucksIds
) {}
