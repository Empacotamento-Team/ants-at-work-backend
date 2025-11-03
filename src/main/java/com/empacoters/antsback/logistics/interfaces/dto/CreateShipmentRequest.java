package com.empacoters.antsback.logistics.interfaces.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class CreateShipmentRequest {
    private Date date;
    private List<Long> loadIds;
}