package com.empacoters.antsback.logistics.infrastructure.entity;

import com.empacoters.antsback.logistics.domain.model.Dimensions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "packagings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackagingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "height", column = @Column(name = "internal_height")),
        @AttributeOverride(name = "width", column = @Column(name = "internal_width")),
        @AttributeOverride(name = "length", column = @Column(name = "internal_length")),
    })
    private Dimensions internalDimensions;
}
