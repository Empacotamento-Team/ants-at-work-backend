package com.empacoters.antsback.logistics.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OptimizationStatus {
    PENDING("Pendente"),
    PROCESSING("Processando"),
    SUCCESS("Sucesso"),
    FAILED("Falhou");

    private final String status;

    OptimizationStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String status() {
        // Retornar o nome do enum em vez da descrição para compatibilidade com o frontend
        return this.name();
    }
    
    public String description() {
        return this.status;
    }
}
