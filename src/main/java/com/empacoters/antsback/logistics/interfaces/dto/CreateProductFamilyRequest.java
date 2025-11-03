package com.empacoters.antsback.logistics.interfaces.dto;

import lombok.Data;

@Data
public class CreateProductFamilyRequest {
    private String name;
    private String description;
    private Double defaultMaxSupportedWeight;
}