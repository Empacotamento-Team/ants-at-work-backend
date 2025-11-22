package com.empacoters.antsback.logistics.interfaces.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateLoadRequest {
    private Long shipmentId;
    private Long truckId;
    private List<Long> packageIds;
}