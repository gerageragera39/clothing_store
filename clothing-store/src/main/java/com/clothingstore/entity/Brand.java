package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private Logo logo;
}
