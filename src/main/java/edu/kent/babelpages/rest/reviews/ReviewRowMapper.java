package edu.kent.babelpages.rest.reviews;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Review(
                rs.getString("id"),
                rs.getString("book_id"),
                rs.getString("user_id"),
                rs.getString("title"),
                rs.getString("body"),
                rs.getInt("score"),
                rs.getInt("useful_votes"),
                rs.getInt("funny_votes"),
                rs.getInt("poetic_votes"),
                rs.getTimestamp("created_at")
        );
    }
}
