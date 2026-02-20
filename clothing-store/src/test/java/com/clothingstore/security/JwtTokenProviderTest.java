package com.clothingstore.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider(
            "test-secret-key-for-unit-tests-min-32-chars",
            900000,
            3600000
        );
    }

    @Test
    public void testGenerateToken() {
        UserDetails userDetails = User.builder()
            .username("test@example.com")
            .password("password")
            .roles("USER")
            .build();

        String token = jwtTokenProvider.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void testExtractUsername() {
        UserDetails userDetails = User.builder()
            .username("test@example.com")
            .password("password")
            .roles("USER")
            .build();

        String token = jwtTokenProvider.generateToken(userDetails);
        String username = jwtTokenProvider.extractUsername(token);

        assertEquals("test@example.com", username);
    }

    @Test
    public void testValidateToken() {
        UserDetails userDetails = User.builder()
            .username("test@example.com")
            .password("password")
            .roles("USER")
            .build();

        String token = jwtTokenProvider.generateToken(userDetails);
        boolean isValid = jwtTokenProvider.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    public void testGenerateRefreshToken() {
        String refreshToken = jwtTokenProvider.generateRefreshToken();

        assertNotNull(refreshToken);
        assertTrue(refreshToken.length() > 0);
    }
}
