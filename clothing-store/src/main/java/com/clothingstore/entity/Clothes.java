package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "clothes_colors",
        joinColumns = @JoinColumn(name = "clothes_id"),
        inverseJoinColumns = @JoinColumn(name = "colors_id")
    )
    private Set<Color> colors = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "clothes_sizes",
        joinColumns = @JoinColumn(name = "clothes_id"),
        inverseJoinColumns = @JoinColumn(name = "sizes_id")
    )
    private Set<Size> sizes = new HashSet<>();

    public enum Sex {
        MALE, FEMALE, UNISEX
    }

    public enum Type {
        TSHIRT, SHIRT, HOODIE, JEANS, SHORTS, DRESS, JACKET, SHOES, ACCESSORIES
    }
}
