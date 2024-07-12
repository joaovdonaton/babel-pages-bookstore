package edu.kent.babelpages.rest.tags;

import edu.kent.babelpages.rest.books.BookRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class TagsDAO {
    private final String SQL_SELECT_ALL_TAGS = "SELECT * FROM tags";
    private final String SQL_SELECT_BY_NAME = "SELECT * FROM tags WHERE name = ?";
    private final String SQL_SELECT_BY_BOOK_ID =
            "SELECT * FROM books_tags JOIN tags ON books_tags.tag_id = tags.id WHERE books_tags.book_id = ?";

    private final JdbcTemplate jdbcTemplate;

    public TagsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> findAll(){
        return jdbcTemplate.query(SQL_SELECT_ALL_TAGS, new TagRowMapper());
    }

    public Tag findByName(String name){
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, new TagRowMapper(), name);
    }

    public List<Tag> findAllByBookId(String bookId){
        return jdbcTemplate.query(SQL_SELECT_BY_BOOK_ID, new TagRowMapper(), bookId);
    }
}
