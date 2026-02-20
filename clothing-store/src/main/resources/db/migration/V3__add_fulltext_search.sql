-- V3__add_fulltext_search.sql - Add full-text search capabilities

-- Create search index table for clothes
CREATE TABLE IF NOT EXISTS clothes_search_index (
    clothes_id BIGINT PRIMARY KEY,
    search_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (clothes_id) REFERENCES clothes(id) ON DELETE CASCADE
);

-- Create index for search
CREATE INDEX IF NOT EXISTS idx_clothes_search_text ON clothes_search_index USING HASH(search_text);

-- Populate search index with existing data
INSERT INTO clothes_search_index (clothes_id, search_text)
SELECT 
    c.id,
    LOWER(
        COALESCE(c.title, '') || ' ' ||
        COALESCE(c.description, '') || ' ' ||
        COALESCE(c.compound, '') || ' ' ||
        COALESCE(c.type, '') || ' ' ||
        COALESCE(c.sex, '') || ' ' ||
        COALESCE(b.name, '')
    )
FROM clothes c
LEFT JOIN brands b ON c.brand_id = b.id;

-- Create view for search results
CREATE OR REPLACE VIEW clothes_search_view AS
SELECT 
    c.id,
    c.title,
    c.sex,
    c.type,
    c.description,
    c.price,
    c.quantity,
    c.enabled,
    c.clg,
    b.name as brand_name,
    si.search_text
FROM clothes c
LEFT JOIN brands b ON c.brand_id = b.id
LEFT JOIN clothes_search_index si ON c.id = si.clothes_id
WHERE c.enabled = TRUE;
