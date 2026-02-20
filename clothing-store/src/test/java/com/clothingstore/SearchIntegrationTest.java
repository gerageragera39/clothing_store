package com.clothingstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SearchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSearchWithoutQuery() throws Exception {
        mockMvc.perform(get("/api/search"))
            .andExpect(status().isOk());
    }

    @Test
    public void testSearchWithQuery() throws Exception {
        mockMvc.perform(get("/api/search")
                .param("query", "t-shirt"))
            .andExpect(status().isOk());
    }

    @Test
    public void testSearchSuggestions() throws Exception {
        mockMvc.perform(get("/api/search/suggestions")
                .param("query", "t"))
            .andExpect(status().isOk());
    }

    @Test
    public void testSearchAll() throws Exception {
        mockMvc.perform(get("/api/search/all"))
            .andExpect(status().isOk());
    }
}
