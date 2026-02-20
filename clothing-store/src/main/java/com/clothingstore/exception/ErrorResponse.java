package com.clothingstore.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final Instant timestamp = Instant.now();
    private final HttpStatus status;
    private final String message;
    private final String path;
}
