package com.clothingstore.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record SearchResponse(
    Long id,
    String title,
    String sex,
    String type,
    String description,
    BigDecimal price,
    Integer quantity,
    String brandName,
    String clg,
    Boolean enabled,
    Double score
) {
    public static SearchResponse fromClothesResponse(ClothesResponse response, String brandName, Double score) {
        return new SearchResponse(
            response.id(),
            response.title(),
            response.sex(),
            response.type(),
            response.description(),
            response.price(),
            response.quantity(),
            brandName,
            response.clg(),
            response.enabled(),
            score
        );
    }
}
