package com.clothingstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClothesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetClothesWithoutAuth() throws Exception {
        // Public endpoint should be accessible
        mockMvc.perform(get("/api/clothes"))
            .andExpect(status().isOk());
    }

    @Test
    public void testCreateClothesWithoutAuth() throws Exception {
        // Admin-only endpoint should require auth
        mockMvc.perform(post("/api/clothes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "title": "Test Item",
                        "sex": "UNISEX",
                        "type": "TSHIRT",
                        "price": 29.99,
                        "quantity": 100
                    }
                    """))
            .andExpect(status().isForbidden());
    }
}
