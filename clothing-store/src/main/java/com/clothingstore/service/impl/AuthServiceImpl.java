package com.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.clothingstore.dto.request.AuthRequest;
import com.clothingstore.dto.request.RegisterRequest;
import com.clothingstore.dto.response.AuthResponse;
import com.clothingstore.entity.RefreshToken;
import com.clothingstore.entity.User;
import com.clothingstore.exception.AuthenticationException;
import com.clothingstore.exception.DuplicateResourceException;
import com.clothingstore.repository.RefreshTokenRepository;
import com.clothingstore.repository.UserRepository;
import com.clothingstore.security.JwtTokenProvider;
import com.clothingstore.service.AuthService;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (Exception e) {
            log.warn("Login failed for email: {}", request.email());
            throw new AuthenticationException("Invalid email or password");
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        return generateAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("User", "email");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(User.Role.USER);
        user.setEnabled(true);

        userRepository.save(user);
        log.info("New user registered: {}", request.email());

        return generateAuthResponse(user);
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException("Invalid refresh token"));

        if (token.getRevoked()) {
            throw new AuthenticationException("Token has been revoked");
        }

        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new AuthenticationException("Refresh token expired");
        }

        // Revoke old token and generate new ones
        token.setRevoked(true);
        refreshTokenRepository.save(token);

        return generateAuthResponse(token.getUser());
    }

    @Override
    @Transactional
    public void logout(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            refreshTokenRepository.deleteByUser_Id(userOpt.get().getId());
            log.info("User logged out: {}", email);
        }
    }

    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtTokenProvider.generateToken(
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole().name())
                        .build()
        );

        String refreshToken = jwtTokenProvider.generateRefreshToken();

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setUser(user);
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiresAt(Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenExpiration()));
        refreshTokenRepository.save(refreshTokenEntity);

        return new AuthResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtTokenProvider.getAccessTokenExpiration(),
                new AuthResponse.UserInfo(user.getId(), user.getEmail(), user.getRole().name())
        );
    }

    // Helper method to get refresh token expiration
    private long getRefreshTokenExpiration() {
        return jwtTokenProvider.getRefreshExpirationMs();
    }
}
