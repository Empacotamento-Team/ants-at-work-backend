package com.empacoters.antsback.logistics.interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FleetCreateDTO {
    @NotBlank(message = "Nome não pode estar vazio!")
    private String name;

    @NotBlank(message = "Código não pode estar vazio!")
    private String code;

    private String placeOfOperation;

    private Long[] trucksIds;
}
