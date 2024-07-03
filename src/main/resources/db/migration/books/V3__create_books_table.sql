CREATE TABLE books(
    id VARCHAR(36) PRIMARY KEY DEFAULT(UUID()),
    title VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    ISBN CHAR(13),
    publication_date DATE,
    language VARCHAR(30),
    price DECIMAL(7, 3),
    stock_quantity INT
)