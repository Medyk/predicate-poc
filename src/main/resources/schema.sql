-- Table: book
-- Description: Stores information about books available in the library.
CREATE TABLE books (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    isbn VARCHAR(255),
    pub_year INT,
    -- Additional columns not mapped to entity can be added here
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE books IS 'Stores information about books available in the library.';
COMMENT ON COLUMN books.id IS 'Unique identifier for the book.';
COMMENT ON COLUMN books.title IS 'Title of the book.';
COMMENT ON COLUMN books.author IS 'Author of the book.';
COMMENT ON COLUMN books.isbn IS 'International Standard Book Number.';
COMMENT ON COLUMN books.pub_year IS 'Year of publication.';
COMMENT ON COLUMN books.created_at IS 'Timestamp when the record was created.';
