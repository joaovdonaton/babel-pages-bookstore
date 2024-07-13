package edu.kent.babelpages.rest.reviews;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ReviewsDAO {
    private final String SQL_INSERT_REVIEW =
            "INSERT INTO reviews (id, book_id, user_id, score, title, body) VALUES (?, ?, ?, ?, ?, ?);";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM reviews WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;

    public ReviewsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Review findById(String id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new ReviewRowMapper(), id);
    }

    public Review save(Review review) {
        String uuidString = UUID.randomUUID().toString();

        jdbcTemplate.update(SQL_INSERT_REVIEW,
                uuidString,
                review.getBookId(),
                review.getUserId(),
                review.getScore(),
                review.getTitle(),
                review.getBody());

        return findById(uuidString);
    }

}
