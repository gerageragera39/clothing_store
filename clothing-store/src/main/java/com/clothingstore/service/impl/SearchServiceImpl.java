package com.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.clothingstore.dto.request.SearchRequest;
import com.clothingstore.dto.response.ClothesResponse;
import com.clothingstore.dto.response.SearchResponse;
import com.clothingstore.entity.Clothes;
import com.clothingstore.repository.SearchRepository;
import com.clothingstore.service.SearchService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SearchResponse> search(SearchRequest request) {
        log.info("Searching for: {}", request.query());

        if (request.query() == null || request.query().isBlank()) {
            return searchAll();
        }

        List<Clothes> results = searchRepository.searchWithFilters(
                request.query(),
                request.sex(),
                request.type(),
                request.minPrice() != null ? request.minPrice().doubleValue() : null,
                request.maxPrice() != null ? request.maxPrice().doubleValue() : null
        );

        return results.stream()
                .map(this::toSearchResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SearchResponse> searchAll() {
        List<Clothes> allClothes = searchRepository.findAll();
        return allClothes.stream()
                .filter(Clothes::getEnabled)
                .map(this::toSearchResponse)
                .collect(Collectors.toList());
    }

    private SearchResponse toSearchResponse(Clothes clothes) {
        String brandName = clothes.getBrand() != null ? clothes.getBrand().getName() : null;
        
        // Calculate a simple relevance score
        Double score = calculateRelevanceScore(clothes, "");
        
        return new SearchResponse(
                clothes.getId(),
                clothes.getTitle(),
                clothes.getSex().name(),
                clothes.getType().name(),
                clothes.getDescription(),
                clothes.getPrice(),
                clothes.getQuantity(),
                brandName,
                clothes.getClg(),
                clothes.getEnabled(),
                score
        );
    }

    private SearchResponse toSearchResponseWithScore(Clothes clothes, String query) {
        String brandName = clothes.getBrand() != null ? clothes.getBrand().getName() : null;
        Double score = calculateRelevanceScore(clothes, query);
        
        return new SearchResponse(
                clothes.getId(),
                clothes.getTitle(),
                clothes.getSex().name(),
                clothes.getType().name(),
                clothes.getDescription(),
                clothes.getPrice(),
                clothes.getQuantity(),
                brandName,
                clothes.getClg(),
                clothes.getEnabled(),
                score
        );
    }

    private Double calculateRelevanceScore(Clothes clothes, String query) {
        if (query == null || query.isBlank()) {
            return 0.0;
        }

        double score = 0.0;
        String queryLower = query.toLowerCase();
        String titleLower = clothes.getTitle().toLowerCase();
        String descLower = clothes.getDescription() != null ? clothes.getDescription().toLowerCase() : "";
        String typeLower = clothes.getType().name().toLowerCase();

        // Title match has highest weight
        if (titleLower.contains(queryLower)) {
            score += 10.0;
            if (titleLower.startsWith(queryLower)) {
                score += 5.0;
            }
        }

        // Description match
        if (descLower.contains(queryLower)) {
            score += 3.0;
        }

        // Type match
        if (typeLower.contains(queryLower)) {
            score += 2.0;
        }

        return score;
    }
}
