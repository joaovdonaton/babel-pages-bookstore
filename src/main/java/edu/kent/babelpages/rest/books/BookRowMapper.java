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
                rs.getDate("publication_date"), // PROBLEM RIGHT NOW IS WE CAN'T CONVERT IF WE HAVE MONTH 00 OR DAY 00
                // authors are in a comma separated VARCHAR column
                new HashSet<>(Arrays.asList(rs.getString("authors").split(",")))
        );
    }
}
