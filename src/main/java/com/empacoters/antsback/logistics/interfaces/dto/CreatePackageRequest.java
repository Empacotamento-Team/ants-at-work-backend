package com.empacoters.antsback.logistics.interfaces.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreatePackageRequest {
    private Long packagingId;
    private List<Long> productIds;
    private Double supportedWeight;
}