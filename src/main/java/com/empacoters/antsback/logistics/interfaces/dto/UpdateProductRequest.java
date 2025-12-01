package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import lombok.Data;

@Data
public class UpdateProductRequest {
    private String name;
    private Long familyId;
    private Dimensions dimensions;
    private Double weight;
    private Double maxSupportedWeight;
    private String batch;
    private boolean fragile;
}

