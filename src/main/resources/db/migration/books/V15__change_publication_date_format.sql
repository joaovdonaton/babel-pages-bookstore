-- we do this because we need the ability to have null months or days
ALTER TABLE books DROP COLUMN publication_date;

ALTER TABLE books ADD COLUMN pub_year YEAR DEFAULT NULL;
ALTER TABLE books ADD COLUMN pub_month int DEFAULT NULL;
ALTER TABLE books ADD COLUMN pub_day int DEFAULT NULL;