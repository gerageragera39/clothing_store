package com.clothingstore.service;

import com.clothingstore.dto.request.SearchRequest;
import com.clothingstore.dto.response.SearchResponse;

import java.util.List;

public interface SearchService {
    List<SearchResponse> search(SearchRequest request);
    List<SearchResponse> searchAll();
}
