package com.empacoters.antsback.identity.domain.model;

import com.empacoters.antsback.shared.vo.Email;

import java.util.Set;

public class User {
    private final Long id;
    private String name;
    private Email email;
    private String passwordHash;
    private Set<UserRole> roles;

    public User(Long id, String name, Email email, String passwordHash, Set<UserRole> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roles = roles;
    }

    public Long id() {
        return this.id;
    }

    // Name
    public String name() {
        return this.name;
    }
    public void changeName(String newName) {
        if (newName == null)
            throw new IllegalArgumentException("Usuário precisa ter um nome válido!");
        if (newName.trim().length() < 3)
            throw new IllegalArgumentException("O nome de usuário precisa ter pelo menos 3 caracteres!");
        this.name = newName.trim();
    }

    // Email
    public Email email() {
        return this.email;
    }
    public void changeEmail(Email newEmail) {
        if (newEmail == null)
            throw new IllegalArgumentException("Usuário precisa ter um endereço de e-mail válido!");
        this.email = newEmail;
    }

    // Password Hash
    public String passwordHash() {
        return this.passwordHash;
    }
    public void changePasswordHash(String newPasswordHash) {
        if (newPasswordHash == null)
            throw new IllegalArgumentException("O password do usuário não pode ser nulo!");
        this.passwordHash = newPasswordHash;
    }

    // Roles
    public Set<UserRole> roles() {
        return this.roles;
    }
}
