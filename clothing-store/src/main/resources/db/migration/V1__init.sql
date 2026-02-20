-- V1__init.sql - Initial schema for Clothing Store
-- Creates core tables: users, roles, brands, clothes, products, images, colors, sizes

-- Users table (base for all user types)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_type VARCHAR(50) NOT NULL
);

-- Admins table (extends users)
CREATE TABLE IF NOT EXISTS admins (
    id BIGINT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_type VARCHAR(50) NOT NULL DEFAULT 'ADMIN'
);

-- Personals table (extends users - regular customers)
CREATE TABLE IF NOT EXISTS personals (
    id BIGINT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_type VARCHAR(50) NOT NULL DEFAULT 'PERSONAL',
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone VARCHAR(50)
);

-- Refresh tokens table
CREATE TABLE IF NOT EXISTS refresh_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(512) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Brands table
CREATE TABLE IF NOT EXISTS brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    name VARCHAR(255) NOT NULL
);

-- Logos table (brand logos)
CREATE TABLE IF NOT EXISTS logos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    url VARCHAR(512) NOT NULL,
    brand_id BIGINT,
    FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE SET NULL
);

-- Colors table
CREATE TABLE IF NOT EXISTS colors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    name VARCHAR(100) NOT NULL
);

-- Sizes table
CREATE TABLE IF NOT EXISTS sizes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    name VARCHAR(50) NOT NULL
);

-- Clothes table
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
    quantity INT NOT NULL DEFAULT 0,
    brand_id BIGINT,
    FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE SET NULL
);

-- Images table (clothes images)
CREATE TABLE IF NOT EXISTS images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    url VARCHAR(512) NOT NULL,
    thing_id BIGINT,
    FOREIGN KEY (thing_id) REFERENCES clothes(id) ON DELETE CASCADE
);

-- Products table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    enabled BOOLEAN DEFAULT TRUE,
    name VARCHAR(255) NOT NULL,
    wear_id BIGINT,
    FOREIGN KEY (wear_id) REFERENCES clothes(id) ON DELETE SET NULL
);

-- Many-to-many: Clothes <-> Colors
CREATE TABLE IF NOT EXISTS clothes_colors (
    clothes_id BIGINT NOT NULL,
    colors_id BIGINT NOT NULL,
    PRIMARY KEY (clothes_id, colors_id),
    FOREIGN KEY (clothes_id) REFERENCES clothes(id) ON DELETE CASCADE,
    FOREIGN KEY (colors_id) REFERENCES colors(id) ON DELETE CASCADE
);

-- Many-to-many: Clothes <-> Sizes
CREATE TABLE IF NOT EXISTS clothes_sizes (
    clothes_id BIGINT NOT NULL,
    sizes_id BIGINT NOT NULL,
    PRIMARY KEY (clothes_id, sizes_id),
    FOREIGN KEY (clothes_id) REFERENCES clothes(id) ON DELETE CASCADE,
    FOREIGN KEY (sizes_id) REFERENCES sizes(id) ON DELETE CASCADE
);

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_clothes_brand ON clothes(brand_id);
CREATE INDEX IF NOT EXISTS idx_clothes_enabled ON clothes(enabled);
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_user ON refresh_tokens(user_id);
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_token ON refresh_tokens(token);
