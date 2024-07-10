package edu.kent.babelpages.rest.books;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BooksDAO {
    private final String SQL_SELECT_ORDER_BY =
            "SELECT * FROM books ORDER BY ? LIMIT ? OFFSET ?";

    private final JdbcTemplate jdbcTemplate;

    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAllOrderBy(String orderBy, int limit, int page){
        List<Book> result = jdbcTemplate.query(SQL_SELECT_ORDER_BY, new BookRowMapper(),
                orderBy, limit, page);

        return result;
    }
}
