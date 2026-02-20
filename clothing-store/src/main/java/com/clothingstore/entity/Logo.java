package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "logos")
public class Logo extends BaseEntity {

    @Column(nullable = false)
    private String url;

    @OneToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
