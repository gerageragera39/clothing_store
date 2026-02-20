package com.clothingstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.clothingstore.entity.Clothes;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long>, JpaSpecificationExecutor<Clothes> {
}
