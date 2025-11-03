package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import lombok.Data;

@Data
public class CreatePackagingRequest {
    private String name;
    private String description;
    private Dimensions internalDimensions;
}