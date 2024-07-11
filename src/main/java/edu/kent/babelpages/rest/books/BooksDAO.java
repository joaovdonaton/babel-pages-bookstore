package edu.kent.babelpages.rest.books;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BooksDAO {
    private static String BUILD_QUERY_SELECT_SEARCH(String orderByColumn) {
        return "SELECT * FROM books ORDER BY " + orderByColumn + " LIMIT ? OFFSET ?";
    }

    private static String BUILD_QUERY_SELECT_SEARCH_WITH_KEYWORD(String orderByColumn) {
        return "SELECT * FROM books " +
                "WHERE title LIKE :keyword OR authors LIKE :keyword ORDER BY "
                + orderByColumn + " LIMIT :limit OFFSET :offset";
    }

    private final String SQL_SELECT_BY_ID = "SELECT * FROM books WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BooksDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Book> findAllOrderBy(String orderBy, int limit, int offset){
        return jdbcTemplate.query(BUILD_QUERY_SELECT_SEARCH(orderBy), new BookRowMapper(),
                limit, offset);
    }

    public List<Book> findAllOrderByWithKeyword(String keyword, String orderBy, int limit, int offset){
        String formattedKeyword = '%' + keyword.trim() + '%';

        return namedParameterJdbcTemplate.query(BUILD_QUERY_SELECT_SEARCH_WITH_KEYWORD(orderBy),
        new MapSqlParameterSource()
                .addValue("keyword", formattedKeyword)
                .addValue("limit", limit)
                .addValue("offset", offset),
        new BookRowMapper());
    }

    public Book findById(String id){
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new BookRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}
