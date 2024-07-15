CREATE TABLE profiles(
    id VARCHAR(36) PRIMARY KEY DEFAULT (NOW()) ,
    user_id VARCHAR(36) NOT NULL,
    profile_picture_url VARCHAR(300),
    country CHAR(2), -- ISO 3166 alpha-2 country code
    bio VARCHAR(1000),
    occupation VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
)