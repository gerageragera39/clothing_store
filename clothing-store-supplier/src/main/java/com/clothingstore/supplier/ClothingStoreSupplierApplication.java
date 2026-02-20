package com.clothingstore.supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClothingStoreSupplierApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClothingStoreSupplierApplication.class, args);
    }
}
