package com.empacoters.antsback.logistics.interfaces.dto;

import com.empacoters.antsback.logistics.domain.model.TruckType;
import lombok.Data;

@Data
public class CreateTruckModelRequest {
    private String name;
    private String description;

    private Double defaultMaximumCapacity;
    private TruckType defaultTruckType;

    private Double defaultInternalLength;
    private Double defaultInternalWidth;
    private Double defaultInternalHeight;
}
