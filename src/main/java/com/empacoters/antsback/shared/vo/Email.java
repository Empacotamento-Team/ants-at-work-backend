package com.empacoters.antsback.shared.vo;

import com.empacoters.antsback.shared.exception.InvalidEmailException;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Email {
    private final String address;

    public Email(String address) {
        if (!address.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidEmailException("Email inválido!");
        }
        this.address = address.toLowerCase();
    }

    public String address() {
        return this.address;
    }

    @Override
    public String toString() {
        return this.address;
    }
}