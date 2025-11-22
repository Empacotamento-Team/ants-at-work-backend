package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private String code;
    private String description;
    private Double weight;
    private Double maxSupportedWeight;
    private String batch;
    private boolean fragile;
    private Dimensions dimensions;
    private Long familyId;
}