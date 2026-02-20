package com.clothingstore.dto.response;

public record AuthResponse(
    String accessToken,
    String refreshToken,
    String tokenType,
    Long expiresIn,
    UserInfo user
) {
    public record UserInfo(
        Long id,
        String email,
        String role
    ) {}
}
