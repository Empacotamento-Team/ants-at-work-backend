package com.empacoters.antsback.logistics.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Shipment {
    private final Long id;
    private List<Load> loads;
    private Instant createdAt;

    public Shipment(Long id, List<Load> loads, Instant createdAt) {
        this.id = id;
        this.loads = loads == null ? new ArrayList<>() : loads;
        this.createdAt = createdAt;
    }

    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @JsonProperty("loads")
    public List<Load> loads() {
        return loads;
    }
    public void changeLoads(List<Load> loads) {
        this.loads = validateLoads(loads);
    }
    public void addLoad(Load load) {
        if (load == null)
            throw new IllegalArgumentException("load is null");

        load.changeShipmentId(this.id);
        loads.add(load);
    }

    @JsonProperty("createdAt")
    public Instant createdAt() {
        return createdAt;
    }

    // Validations
    private List<Load> validateLoads(List<Load> loads) {
        if (loads == null)
            throw new IllegalArgumentException("A lista de cargas da carga total não pode ser nula");
        return loads;
    }

    private Instant validateDate(Instant date) {
        if (date == null)
            throw new IllegalArgumentException("A data da carga total não pode ser nula");
        return date;
    }
}
