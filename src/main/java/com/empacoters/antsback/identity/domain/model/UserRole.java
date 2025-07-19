package com.empacoters.antsback.identity.domain.model;

public enum UserRole {
    ADMIN("Administrador");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }

    public String toAuthority() {
        return "ROLE_" + name();
    }
}
