-- V1__init.sql - Initial schema for Clothing Store Supplier
-- Simplified schema for supplier API

-- Clothes table (supplier version)
CREATE TABLE IF NOT EXISTS clothes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    title VARCHAR(255) NOT NULL,
    sex VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    compound TEXT,
    clg VARCHAR(100),
    price DECIMAL(10, 2) NOT NULL DEFAULT 0,
    quantity INT NOT NULL DEFAULT 0
);

-- Images table
CREATE TABLE IF NOT EXISTS images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    url VARCHAR(512) NOT NULL,
    thing_id BIGINT,
    FOREIGN KEY (thing_id) REFERENCES clothes(id) ON DELETE CASCADE
);

-- API Tokens table (for inter-service communication)
CREATE TABLE IF NOT EXISTS api_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(512) NOT NULL UNIQUE,
    description VARCHAR(255),
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_clothes_enabled ON clothes(enabled);
CREATE INDEX IF NOT EXISTS idx_clothes_type ON clothes(type);
CREATE INDEX IF NOT EXISTS idx_api_tokens_token ON api_tokens(token);
