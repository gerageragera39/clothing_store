package com.clothingstore.supplier.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clothes")
public class Clothes extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String compound;

    private String clg;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    public enum Sex {
        MALE, FEMALE, UNISEX
    }

    public enum Type {
        TSHIRT, SHIRT, HOODIE, JEANS, SHORTS, DRESS, JACKET, SHOES, ACCESSORIES
    }
}
