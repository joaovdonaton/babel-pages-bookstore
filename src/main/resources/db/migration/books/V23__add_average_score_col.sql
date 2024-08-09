ALTER TABLE books ADD COLUMN avg_score DECIMAL(3, 2) DEFAULT NULL;
ALTER TABLE books ADD CONSTRAINT avg_score_positive CHECK(avg_score >= 0);