package com.empacoters.antsback.identity.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;

public enum UserRole {
    ADMIN("Administrador"),
    MANAGER("Gestor"),
    WORKER("Operador");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    @JsonValue
    public String description() {
        return this.description;
    }

    public String toAuthority() {
        return "ROLE_" + name();
    }

    public static Optional<UserRole> fromDescription(String description) {
        for (UserRole role : values()) {
            if (role.description.equalsIgnoreCase(description))
                return Optional.of(role);
        }
        return Optional.empty();
    }
}
