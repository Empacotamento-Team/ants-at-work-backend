package com.empacoters.antsback.logistics.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;

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

    public static Optional<TruckStatus> fromDescription(String description) {
        for (TruckStatus status: values()){
            if(status.description.equalsIgnoreCase(description))
                return Optional.of(status);
        }
        return Optional.empty();
    }
}
