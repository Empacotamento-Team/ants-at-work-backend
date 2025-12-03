package com.empacoters.antsback.logistics.interfaces.dto;

import lombok.Data;

@Data
public class CreatePackageRequest {
    // Usar packaging existente
    private Long packagingId;
    
    // Ou criar uma nova packaging
    private String packagingName;
    private String packagingDescription;
    private Double packagingHeight;
    private Double packagingWidth;
    private Double packagingLength;
    
    // Informações do package
    private Long productId;
}