-- V2__seed_dev.sql - Development seed data
-- Creates default admin user and sample data for development

-- Default admin user (password: admin123)
INSERT INTO users (id, email, password, role_type, enabled) 
VALUES (1, 'admin@store.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', TRUE)
ON DUPLICATE KEY UPDATE email=email;

INSERT INTO admins (id, email, password, role_type, enabled)
VALUES (1, 'admin@store.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', TRUE)
ON DUPLICATE KEY UPDATE email=email;

-- Sample brands
INSERT INTO brands (id, name, enabled) VALUES (1, 'Nike', TRUE);
INSERT INTO brands (id, name, enabled) VALUES (2, 'Adidas', TRUE);
INSERT INTO brands (id, name, enabled) VALUES (3, 'Puma', TRUE);
INSERT INTO brands (id, name, enabled) VALUES (4, 'Zara', TRUE);
INSERT INTO brands (id, name, enabled) VALUES (5, 'H&M', TRUE);

-- Sample colors
INSERT INTO colors (id, name, enabled) VALUES (1, 'Black', TRUE);
INSERT INTO colors (id, name, enabled) VALUES (2, 'White', TRUE);
INSERT INTO colors (id, name, enabled) VALUES (3, 'Red', TRUE);
INSERT INTO colors (id, name, enabled) VALUES (4, 'Blue', TRUE);
INSERT INTO colors (id, name, enabled) VALUES (5, 'Green', TRUE);
INSERT INTO colors (id, name, enabled) VALUES (6, 'Grey', TRUE);

-- Sample sizes
INSERT INTO sizes (id, name, enabled) VALUES (1, 'XS', TRUE);
INSERT INTO sizes (id, name, enabled) VALUES (2, 'S', TRUE);
INSERT INTO sizes (id, name, enabled) VALUES (3, 'M', TRUE);
INSERT INTO sizes (id, name, enabled) VALUES (4, 'L', TRUE);
INSERT INTO sizes (id, name, enabled) VALUES (5, 'XL', TRUE);
INSERT INTO sizes (id, name, enabled) VALUES (6, 'XXL', TRUE);

-- Sample clothes
INSERT INTO clothes (id, title, sex, type, description, price, quantity, brand_id, enabled)
VALUES (1, 'Nike Air T-Shirt', 'Male', 'TShirt', 'Classic cotton t-shirt with Nike logo', 49.99, 100, 1, TRUE);

INSERT INTO clothes (id, title, sex, type, description, price, quantity, brand_id, enabled)
VALUES (2, 'Adidas Hoodie', 'Male', 'Hoodie', 'Comfortable hoodie for everyday wear', 89.99, 50, 2, TRUE);

INSERT INTO clothes (id, title, sex, type, description, price, quantity, brand_id, enabled)
VALUES (3, 'Puma Running Shorts', 'Male', 'Shorts', 'Lightweight shorts for running', 34.99, 75, 3, TRUE);

INSERT INTO clothes (id, title, sex, type, description, price, quantity, brand_id, enabled)
VALUES (4, 'Zara Summer Dress', 'Female', 'Dress', 'Elegant summer dress', 79.99, 30, 4, TRUE);

INSERT INTO clothes (id, title, sex, type, description, price, quantity, brand_id, enabled)
VALUES (5, 'H&M Basic Jeans', 'Female', 'Jeans', 'Classic fit jeans', 59.99, 60, 5, TRUE);

-- Link clothes to colors
INSERT INTO clothes_colors (clothes_id, colors_id) VALUES (1, 1); -- Black Nike T-Shirt
INSERT INTO clothes_colors (clothes_id, colors_id) VALUES (1, 2); -- White Nike T-Shirt
INSERT INTO clothes_colors (clothes_id, colors_id) VALUES (2, 6); -- Grey Adidas Hoodie
INSERT INTO clothes_colors (clothes_id, colors_id) VALUES (3, 1); -- Black Puma Shorts
INSERT INTO clothes_colors (clothes_id, colors_id) VALUES (4, 3); -- Red Zara Dress
INSERT INTO clothes_colors (clothes_id, colors_id) VALUES (5, 4); -- Blue H&M Jeans

-- Link clothes to sizes
INSERT INTO clothes_sizes (clothes_id, sizes_id) VALUES (1, 2), (1, 3), (1, 4), (1, 5);
INSERT INTO clothes_sizes (clothes_id, sizes_id) VALUES (2, 3), (2, 4), (2, 5);
INSERT INTO clothes_sizes (clothes_id, sizes_id) VALUES (3, 2), (3, 3), (3, 4);
INSERT INTO clothes_sizes (clothes_id, sizes_id) VALUES (4, 2), (4, 3), (4, 4);
INSERT INTO clothes_sizes (clothes_id, sizes_id) VALUES (5, 3), (5, 4), (5, 5);

-- Sample images
INSERT INTO images (url, thing_id, enabled) VALUES ('https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500', 1, TRUE);
INSERT INTO images (url, thing_id, enabled) VALUES ('https://images.unsplash.com/photo-1556905055-8f358a7a47b2?w=500', 2, TRUE);
INSERT INTO images (url, thing_id, enabled) VALUES ('https://images.unsplash.com/photo-1591195853828-11db59a44f6b?w=500', 3, TRUE);
INSERT INTO images (url, thing_id, enabled) VALUES ('https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=500', 4, TRUE);
INSERT INTO images (url, thing_id, enabled) VALUES ('https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=500', 5, TRUE);
