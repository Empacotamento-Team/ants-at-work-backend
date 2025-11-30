package com.empacoters.antsback.logistics.interfaces.dto;

public record OptimizerRequestContainerRowDTO(
    Long containerId,
    Double width,
    Double height,
    Double length,
    Double supportedWeight
) {}
