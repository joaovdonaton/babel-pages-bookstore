CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(40) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(70) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    role CHAR(5) DEFAULT 'USER'
)