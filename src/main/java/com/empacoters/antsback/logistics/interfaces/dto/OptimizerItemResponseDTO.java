package com.empacoters.antsback.logistics.interfaces.dto;

public record OptimizerItemResponseDTO(
    Integer id_item,
    Double x,
    Double y,
    Double z,
    Double peso,
    Double familia,
    Double l,
    Double w,
    Double h,
    String orientacao
) {}
