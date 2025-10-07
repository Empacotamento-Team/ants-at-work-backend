package com.empacoters.antsback.logistics.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TruckStatus {
    UNDER_MAINTENANCE("Em Manutenção"),
    AVAILABLE("Ativo");

    private final String description;

    TruckStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String description() {
        return this.description;
    }
}
