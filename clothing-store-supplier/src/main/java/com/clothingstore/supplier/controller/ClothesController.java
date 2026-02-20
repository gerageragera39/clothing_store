package com.clothingstore.supplier.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clothingstore.supplier.entity.Clothes;
import com.clothingstore.supplier.repository.ClothesRepository;

import java.util.List;

@RestController
@RequestMapping("/api/clothes")
@RequiredArgsConstructor
@Tag(name = "Supplier Clothes", description = "Supplier clothes endpoints")
public class ClothesController {

    private final ClothesRepository clothesRepository;

    @GetMapping
    public ResponseEntity<List<Clothes>> getAll() {
        return ResponseEntity.ok(clothesRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clothes> getById(@PathVariable Long id) {
        return clothesRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Clothes> create(@RequestBody Clothes clothes) {
        return ResponseEntity.ok(clothesRepository.save(clothes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clothes> update(@PathVariable Long id, @RequestBody Clothes clothes) {
        return clothesRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(clothes.getTitle());
                    existing.setSex(clothes.getSex());
                    existing.setType(clothes.getType());
                    existing.setDescription(clothes.getDescription());
                    existing.setCompound(clothes.getCompound());
                    existing.setClg(clothes.getClg());
                    existing.setPrice(clothes.getPrice());
                    existing.setQuantity(clothes.getQuantity());
                    return ResponseEntity.ok(clothesRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clothesRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
