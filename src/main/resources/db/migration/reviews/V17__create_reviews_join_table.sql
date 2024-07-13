CREATE TABLE reviews(
    id VARCHAR(36) PRIMARY KEY DEFAULT (UUID()),
    book_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    score INT NOT NULL CHECK(score BETWEEN 0 AND 10),
    title VARCHAR(150) NOT NULL,
    body VARCHAR(2000) NOT NULL,
    useful_votes INT DEFAULT (0),
    funny_votes INT DEFAULT (0),
    poetic_votes INT DEFAULT (0),
    created_at TIMESTAMP default (NOW()),
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
)