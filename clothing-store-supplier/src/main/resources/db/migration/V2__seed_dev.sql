-- V2__seed_dev.sql - Development seed data for Supplier

-- Sample clothes for supplier
INSERT INTO clothes (id, title, sex, type, description, price, quantity, enabled)
VALUES (1, 'Supplier T-Shirt Basic', 'Unisex', 'TShirt', 'Basic cotton t-shirt from supplier', 25.00, 500, TRUE);

INSERT INTO clothes (id, title, sex, type, description, price, quantity, enabled)
VALUES (2, 'Supplier Hoodie Premium', 'Unisex', 'Hoodie', 'Premium quality hoodie', 45.00, 200, TRUE);

INSERT INTO clothes (id, title, sex, type, description, price, quantity, enabled)
VALUES (3, 'Supplier Jeans Classic', 'Unisex', 'Jeans', 'Classic fit jeans', 35.00, 300, TRUE);

-- Sample images
INSERT INTO images (url, thing_id, enabled) VALUES ('https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500', 1, TRUE);
INSERT INTO images (url, thing_id, enabled) VALUES ('https://images.unsplash.com/photo-1556905055-8f358a7a47b2?w=500', 2, TRUE);
INSERT INTO images (url, thing_id, enabled) VALUES ('https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=500', 3, TRUE);

-- Default API token for main store communication
INSERT INTO api_tokens (token, description, enabled)
VALUES ('supplier-api-token-dev', 'Development token for main store', TRUE);
