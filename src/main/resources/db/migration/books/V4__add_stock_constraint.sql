ALTER TABLE books ADD CONSTRAINT not_negative_stock CHECK (stock_quantity >= 0);