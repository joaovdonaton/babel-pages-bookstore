package edu.kent.babelpages.rest.books;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(UUID.fromString(rs.getString("id")),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("ISBN"),
                rs.getString("language"),
                rs.getString("cover_url"),
                rs.getBigDecimal("price"),
                rs.getInt("stock_quantity"),
                rs.getInt("pub_year"),
                rs.getInt("pub_month"),
                rs.getInt("pub_day"),
                // authors are in a comma separated VARCHAR column
                new HashSet<>(Arrays.asList(rs.getString("authors").split(",")))
        );
    }
}
