package com.empacoters.antsback.logistics.domain.model;

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

    public  Long id() { return id; }

    public LocalDate date() {
        return date;
    }

    public String description() {
        return description;
    }
}
