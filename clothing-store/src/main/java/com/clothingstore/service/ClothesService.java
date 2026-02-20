package com.clothingstore.service;

import org.springframework.data.domain.Page;
import com.clothingstore.dto.request.ClothesCreateRequest;
import com.clothingstore.dto.response.ClothesResponse;

import java.util.Optional;

public interface ClothesService {
    Page<ClothesResponse> getAll(int page, int size, String sex, String type, Long brandId);
    Optional<ClothesResponse> getById(Long id);
    ClothesResponse create(ClothesCreateRequest request);
    ClothesResponse update(Long id, ClothesCreateRequest request);
    void delete(Long id);
}
