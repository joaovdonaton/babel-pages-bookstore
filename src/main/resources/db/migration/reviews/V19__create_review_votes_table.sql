CREATE TABLE review_votes(
    id INT PRIMARY KEY AUTO_INCREMENT,
    review_id VARCHAR(36) NOT NULL ,
    user_id VARCHAR(36) NOT NULL,
    type CHAR(6) CHECK (type = 'POETIC' OR type = 'FUNNY' OR type = 'USEFUL'),
    created_at TIMESTAMP DEFAULT(NOW()),
    FOREIGN KEY (review_id) REFERENCES reviews(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
)