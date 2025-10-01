package com.empacoters.antsback.logistics.domain.model;

public enum TruckStatus {
    UNDER_MAINTENANCE("Em Manutenção"),
    AVAILABLE("Ativo");

    private final String description;

    TruckStatus(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }
}
