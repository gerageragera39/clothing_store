package com.clothingstore.supplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.clothingstore.supplier.entity.ApiToken;

import java.util.Optional;

@Repository
public interface ApiTokenRepository extends JpaRepository<ApiToken, Long> {
    Optional<ApiToken> findByToken(String token);
}
