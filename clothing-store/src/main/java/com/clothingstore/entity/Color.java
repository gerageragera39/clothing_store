package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "colors")
public class Color extends BaseEntity {

    @Column(nullable = false)
    private String name;
}
