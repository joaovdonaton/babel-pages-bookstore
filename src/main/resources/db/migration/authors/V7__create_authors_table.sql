CREATE TABLE authors (
    id VARCHAR(36) PRIMARY KEY DEFAULT(UUID()),
    name varchar(100) NOT NULL
)