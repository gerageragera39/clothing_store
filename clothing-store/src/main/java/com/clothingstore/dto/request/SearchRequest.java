package com.clothingstore.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record SearchRequest(
    @Size(min = 2, message = "Search query must be at least 2 characters")
    String query,
    
    String sex,
    String type,
    
    @Min(value = 0, message = "Min price must be positive")
    BigDecimal minPrice,
    
    @Min(value = 0, message = "Max price must be positive")
    BigDecimal maxPrice,
    
    @Min(value = 0, message = "Page must be non-negative")
    Integer page,
    
    @Min(value = 1, message = "Size must be at least 1")
    Integer size
) {
    public SearchRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
    }
}
