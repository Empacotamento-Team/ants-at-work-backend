package com.empacoters.antsback.logistics.interfaces.dto;

public record OptimizerRequestDTO(
    OptimizerRequestItemRowDTO[] items,
    OptimizerRequestContainerRowDTO[] containers
) {}
