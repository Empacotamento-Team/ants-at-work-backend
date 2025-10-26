package com.empacoters.antsback.logistics.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;

public enum TruckType {
    CHEST("Baú"),
    CART("Carreta");

    private final String description;

    TruckType(String description){
        this.description = description;
    }

    @JsonValue
    public String description(){
        return this.description;
    }

    public static Optional<TruckType> fromDescription(String description) {
        for (TruckType type: values()){
            if(type.description.equalsIgnoreCase(description))
                return Optional.of(type);
        }
        return Optional.empty();
    }
}
