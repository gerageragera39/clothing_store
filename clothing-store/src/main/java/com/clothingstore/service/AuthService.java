package com.clothingstore.service;

import com.clothingstore.dto.request.AuthRequest;
import com.clothingstore.dto.request.RegisterRequest;
import com.clothingstore.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(RegisterRequest request);
    AuthResponse refreshToken(String refreshToken);
    void logout(String email);
}
