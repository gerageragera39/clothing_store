package com.clothingstore.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

public record ClothesCreateRequest(
    @NotBlank(message = "Title is required")
    String title,

    @NotNull(message = "Sex is required")
    String sex,

    @NotNull(message = "Type is required")
    String type,

    String description,
    String compound,
    String clg,

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    BigDecimal price,

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    Integer quantity,

    Long brandId,
    Set<Long> colorIds,
    Set<Long> sizeIds
) {
}
