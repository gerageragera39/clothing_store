package com.clothingstore.supplier.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "api_tokens")
public class ApiToken extends BaseEntity {

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    private String description;

    @Column(name = "expires_at")
    private Instant expiresAt;
}
