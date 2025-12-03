package com.empacoters.antsback.logistics.interfaces.dto;

public record OptimizerRequestContainerRowDTO(
    Long containerId,
    Double length,
    Double width,
    Double height,
    Double supportedWeight
) {}
