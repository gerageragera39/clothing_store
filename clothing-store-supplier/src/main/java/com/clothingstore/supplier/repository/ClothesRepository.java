package com.clothingstore.supplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.clothingstore.supplier.entity.Clothes;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Long> {
}
