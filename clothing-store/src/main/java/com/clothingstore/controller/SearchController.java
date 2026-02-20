package com.clothingstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clothingstore.dto.request.SearchRequest;
import com.clothingstore.dto.response.SearchResponse;
import com.clothingstore.service.SearchService;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "Full-text search endpoints")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    @Operation(summary = "Search clothes with filters")
    public ResponseEntity<List<SearchResponse>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        SearchRequest request = new SearchRequest(query, sex, type, 
                minPrice != null ? java.math.BigDecimal.valueOf(minPrice) : null,
                maxPrice != null ? java.math.BigDecimal.valueOf(maxPrice) : null,
                page, size);
        
        List<SearchResponse> results = searchService.search(request);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    @Operation(summary = "Search clothes with JSON body")
    public ResponseEntity<List<SearchResponse>> searchPost(@Valid @RequestBody SearchRequest request) {
        List<SearchResponse> results = searchService.search(request);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all searchable clothes")
    public ResponseEntity<List<SearchResponse>> getAll() {
        return ResponseEntity.ok(searchService.searchAll());
    }

    @GetMapping("/suggestions")
    @Operation(summary = "Get search suggestions")
    public ResponseEntity<List<String>> getSuggestions(
            @RequestParam String query
    ) {
        // Return unique titles that match the query
        List<String> suggestions = searchService.search(new SearchRequest(
                        query, null, null, null, null, 0, 10))
                .stream()
                .map(SearchResponse::title)
                .limit(5)
                .toList();
        
        return ResponseEntity.ok(suggestions);
    }
}
