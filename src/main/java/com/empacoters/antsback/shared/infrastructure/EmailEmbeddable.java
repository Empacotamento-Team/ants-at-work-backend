package com.empacoters.antsback.shared.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Embeddable
public class EmailEmbeddable {
    @Getter
    @Column(nullable = false, unique = true)
    private String value;

    protected EmailEmbeddable() {}

    public EmailEmbeddable(String value) {
        this.value = value.toLowerCase();
    }

    @Override
    public String toString() {
        return this.value;
    }
}
