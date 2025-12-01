package com.empacoters.antsback.logistics.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class MaintenanceRecord {
    private final Long id;
    private final LocalDate date;
    private final String description;

    public MaintenanceRecord(Long id, LocalDate date, String description) {
        this.id = id;
        this.date = date;
        this.description = description;
    }

    @JsonProperty("id")
    public  Long id() { return id; }

    @JsonProperty("date")
    public LocalDate date() {
        return date;
    }

    @JsonProperty("description")
    public String description() {
        return description;
    }
}
