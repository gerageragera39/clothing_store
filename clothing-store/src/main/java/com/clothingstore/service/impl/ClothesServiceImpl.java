package com.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.clothingstore.dto.request.ClothesCreateRequest;
import com.clothingstore.dto.response.ClothesResponse;
import com.clothingstore.entity.Brand;
import com.clothingstore.entity.Clothes;
import com.clothingstore.entity.Color;
import com.clothingstore.entity.Size;
import com.clothingstore.exception.ResourceNotFoundException;
import com.clothingstore.repository.BrandRepository;
import com.clothingstore.repository.ClothesRepository;
import com.clothingstore.repository.ColorRepository;
import com.clothingstore.repository.SizeRepository;
import com.clothingstore.service.ClothesService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService {

    private final ClothesRepository clothesRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ClothesResponse> getAll(int page, int size, String sex, String type, Long brandId) {
        Pageable pageable = PageRequest.of(page, size);
        
        Specification<Clothes> spec = Specification.where(null);
        
        if (sex != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("sex"), sex.toUpperCase()));
        }
        
        if (type != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("type"), type.toUpperCase()));
        }
        
        if (brandId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("brand").get("id"), brandId));
        }
        
        spec = spec.and((root, query, cb) -> cb.isTrue(root.get("enabled")));
        
        return clothesRepository.findAll(spec, pageable)
                .map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClothesResponse> getById(Long id) {
        return clothesRepository.findById(id)
                .filter(Clothes::getEnabled)
                .map(this::toResponse);
    }

    @Override
    @Transactional
    public ClothesResponse create(ClothesCreateRequest request) {
        Clothes clothes = new Clothes();
        updateClothesFromRequest(clothes, request);
        clothesRepository.save(clothes);
        return toResponse(clothes);
    }

    @Override
    @Transactional
    public ClothesResponse update(Long id, ClothesCreateRequest request) {
        Clothes clothes = clothesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clothes", id));
        
        updateClothesFromRequest(clothes, request);
        clothesRepository.save(clothes);
        return toResponse(clothes);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Clothes clothes = clothesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clothes", id));
        clothes.setEnabled(false);
        clothesRepository.save(clothes);
    }

    private void updateClothesFromRequest(Clothes clothes, ClothesCreateRequest request) {
        clothes.setTitle(request.title());
        clothes.setSex(Clothes.Sex.valueOf(request.sex().toUpperCase()));
        clothes.setType(Clothes.Type.valueOf(request.type().toUpperCase()));
        clothes.setDescription(request.description());
        clothes.setCompound(request.compound());
        clothes.setClg(request.clg());
        clothes.setPrice(request.price());
        clothes.setQuantity(request.quantity());

        if (request.brandId() != null) {
            Brand brand = brandRepository.findById(request.brandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand", request.brandId()));
            clothes.setBrand(brand);
        }

        if (request.colorIds() != null && !request.colorIds().isEmpty()) {
            Set<Color> colors = new HashSet<>(colorRepository.findAllById(request.colorIds()));
            clothes.setColors(colors);
        }

        if (request.sizeIds() != null && !request.sizeIds().isEmpty()) {
            Set<Size> sizes = new HashSet<>(sizeRepository.findAllById(request.sizeIds()));
            clothes.setSizes(sizes);
        }
    }

    private ClothesResponse toResponse(Clothes clothes) {
        return new ClothesResponse(
                clothes.getId(),
                clothes.getTitle(),
                clothes.getSex().name(),
                clothes.getType().name(),
                clothes.getDescription(),
                clothes.getCompound(),
                clothes.getClg(),
                clothes.getPrice(),
                clothes.getQuantity(),
                clothes.getBrand() != null ? 
                    new ClothesResponse.BrandInfo(clothes.getBrand().getId(), clothes.getBrand().getName()) : null,
                clothes.getImages().stream()
                    .map(img -> new ClothesResponse.ImageInfo(img.getId(), img.getUrl()))
                    .collect(java.util.stream.Collectors.toSet()),
                clothes.getColors().stream()
                    .map(Color::getName)
                    .collect(java.util.stream.Collectors.toSet()),
                clothes.getSizes().stream()
                    .map(Size::getName)
                    .collect(java.util.stream.Collectors.toSet()),
                clothes.getEnabled(),
                clothes.getCreatedAt()
        );
    }
}
