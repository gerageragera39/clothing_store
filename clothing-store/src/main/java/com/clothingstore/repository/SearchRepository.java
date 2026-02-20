package com.clothingstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.clothingstore.entity.Clothes;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Clothes, Long>, JpaSpecificationExecutor<Clothes> {

    @Query(value = """
        SELECT c.* FROM clothes c
        LEFT JOIN brands b ON c.brand_id = b.id
        WHERE c.enabled = true
        AND (
            LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.type) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
        ORDER BY 
            CASE WHEN LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) THEN 0 ELSE 1 END,
            CASE WHEN LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%')) THEN 0 ELSE 1 END
        """, nativeQuery = true)
    List<Clothes> searchByQuery(@Param("query") String query);

    @Query(value = """
        SELECT c.* FROM clothes c
        LEFT JOIN brands b ON c.brand_id = b.id
        WHERE c.enabled = true
        AND (
            LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.type) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
        AND (:sex IS NULL OR c.sex = :sex)
        AND (:type IS NULL OR c.type = :type)
        AND (:minPrice IS NULL OR c.price >= :minPrice)
        AND (:maxPrice IS NULL OR c.price <= :maxPrice)
        ORDER BY 
            CASE WHEN LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) THEN 0 ELSE 1 END,
            CASE WHEN LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%')) THEN 0 ELSE 1 END
        """, nativeQuery = true)
    List<Clothes> searchWithFilters(
        @Param("query") String query,
        @Param("sex") String sex,
        @Param("type") String type,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice
    );
}
