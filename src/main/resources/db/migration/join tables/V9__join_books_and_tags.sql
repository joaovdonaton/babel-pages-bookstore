CREATE TABLE books_tags(
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id VARCHAR(36) NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
)