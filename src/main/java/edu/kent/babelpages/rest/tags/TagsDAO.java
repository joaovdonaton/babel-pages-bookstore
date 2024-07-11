package edu.kent.babelpages.rest.tags;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagsDAO {
    private final String SQL_SELECT_ALL_TAGS = "SELECT * FROM tags";

    private final JdbcTemplate jdbcTemplate;

    public TagsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Tag> findAll(){
        return jdbcTemplate.query(SQL_SELECT_ALL_TAGS, new TagRowMapper());
    }
}
