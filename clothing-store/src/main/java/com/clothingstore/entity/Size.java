package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sizes")
public class Size extends BaseEntity {

    @Column(nullable = false)
    private String name;
}
