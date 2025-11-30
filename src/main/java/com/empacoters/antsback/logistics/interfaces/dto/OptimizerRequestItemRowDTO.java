package com.empacoters.antsback.logistics.interfaces.dto;

public record OptimizerRequestItemRowDTO(
    Long itemId,
    String itemDescription,
    Long familyId,
    String familyName,
    String itemBatch,
    Double weight,
    Double supportedWeight,
    Double height,
    Double width,
    Double length
) {}
