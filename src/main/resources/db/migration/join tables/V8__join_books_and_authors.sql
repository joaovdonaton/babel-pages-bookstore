CREATE TABLE books_authors(
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id VARCHAR(36) NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (author_id) REFERENCES authors(id)
)