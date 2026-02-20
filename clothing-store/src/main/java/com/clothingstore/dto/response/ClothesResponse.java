package com.clothingstore.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record ClothesResponse(
    Long id,
    String title,
    String sex,
    String type,
    String description,
    String compound,
    String clg,
    BigDecimal price,
    Integer quantity,
    BrandInfo brand,
    Set<ImageInfo> images,
    Set<String> colors,
    Set<String> sizes,
    Boolean enabled,
    Instant createdAt
) {
    public record BrandInfo(
        Long id,
        String name
    ) {}

    public record ImageInfo(
        Long id,
        String url
    ) {}
}
