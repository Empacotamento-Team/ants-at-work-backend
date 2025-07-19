package com.empacoters.antsback.identity.infrastructure.entity;

import com.empacoters.antsback.identity.domain.model.UserRole;
import com.empacoters.antsback.shared.infrastructure.EmailEmbeddable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverride(
        name = "value",
        column = @Column(name = "email", nullable = false)
    )
    private EmailEmbeddable email;

    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private Set<UserRole> roles;
}
