package com.clothingstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.clothingstore.dto.request.ClothesCreateRequest;
import com.clothingstore.dto.response.ClothesResponse;
import com.clothingstore.service.ClothesService;

import java.util.List;

@RestController
@RequestMapping("/api/clothes")
@RequiredArgsConstructor
@Tag(name = "Clothes", description = "Clothes management endpoints")
public class ClothesController {

    private final ClothesService clothesService;

    @GetMapping
    @Operation(summary = "Get all clothes with filters")
    public ResponseEntity<List<ClothesResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long brandId
    ) {
        var result = clothesService.getAll(page, size, sex, type, brandId);
        return ResponseEntity.ok(result.getContent());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get clothes by ID")
    public ResponseEntity<ClothesResponse> getById(@PathVariable Long id) {
        return clothesService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create new clothes (Admin only)")
    public ResponseEntity<ClothesResponse> create(@Valid @RequestBody ClothesCreateRequest request) {
        return ResponseEntity.ok(clothesService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update clothes (Admin only)")
    public ResponseEntity<ClothesResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ClothesCreateRequest request
    ) {
        return ResponseEntity.ok(clothesService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete clothes (Admin only)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clothesService.delete(id);
        return ResponseEntity.ok().build();
    }
}
