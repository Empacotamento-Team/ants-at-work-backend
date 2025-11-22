package com.empacoters.antsback.logistics.domain.model;

import java.util.Date;
import java.util.List;

public class Shipment {
    private final Long id;
    private List<Load> loads;
    private Date date;

    public Shipment(Long id, List<Load> loads, Date date) {
        this.id = id;
        this.loads = validateLoads(loads);
        this.date = validateDate(date);
    }

    public Long id() {
        return id;
    }

    public List<Load> loads() {
        return loads;
    }
    public void changeLoads(List<Load> loads) {
        this.loads = validateLoads(loads);
    }

    public Date date() {
        return date;
    }
    public void changeDate(Date date) {
        this.date = validateDate(date);
    }

    // Validations
    private List<Load> validateLoads(List<Load> loads) {
        if (loads == null)
            throw new IllegalArgumentException("A lista de cargas da carga total não pode ser nula");
        return loads;
    }

    private Date validateDate(Date date) {
        if (date == null)
            throw new IllegalArgumentException("A data da carga total não pode ser nula");
        return date;
    }
}
