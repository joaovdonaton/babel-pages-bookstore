ALTER TABLE books MODIFY COLUMN avg_score DECIMAL(4, 2) DEFAULT NULL;
ALTER TABLE books ADD CONSTRAINT avg_score_less_than_equal_to_10 CHECK (avg_score <= 10);