package com.clothingstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistration() throws Exception {
        String requestBody = """
            {
                "email": "test@example.com",
                "password": "password123",
                "firstName": "Test",
                "lastName": "User"
            }
            """;

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").exists())
            .andExpect(jsonPath("$.refreshToken").exists())
            .andExpect(jsonPath("$.user.email").value("test@example.com"));
    }

    @Test
    public void testLogin() throws Exception {
        // First register
        String registerBody = """
            {
                "email": "login@example.com",
                "password": "password123",
                "firstName": "Login",
                "lastName": "Test"
            }
            """;

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerBody));

        // Then login
        String loginBody = """
            {
                "email": "login@example.com",
                "password": "password123"
            }
            """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").exists())
            .andExpect(jsonPath("$.user.email").value("login@example.com"));
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        String loginBody = """
            {
                "email": "notexist@example.com",
                "password": "wrongpassword"
            }
            """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginBody))
            .andExpect(status().isUnauthorized());
    }
}
