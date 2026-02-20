package com.clothingstore.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resource, String field) {
        super(String.format("%s with %s already exists", resource, field));
    }
}
