package com.clothingstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image extends BaseEntity {

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thing_id")
    private Clothes clothes;
}
