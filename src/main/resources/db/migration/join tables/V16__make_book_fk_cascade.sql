ALTER TABLE books_tags DROP FOREIGN KEY books_tags_ibfk_1;
ALTER TABLE books_tags DROP FOREIGN KEY books_tags_ibfk_2;

ALTER TABLE books_tags ADD FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE;
ALTER TABLE books_tags ADD FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE;