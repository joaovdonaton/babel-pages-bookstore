package edu.kent.babelpages.rest.reviews;

import edu.kent.babelpages.rest.reviews.DTO.ReviewResponseDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ReviewsDAO {
    private final String SQL_INSERT_REVIEW =
            "INSERT INTO reviews (id, book_id, user_id, score, title, body) VALUES (?, ?, ?, ?, ?, ?);";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM reviews WHERE id = ?;";

    // a little different because we want to select columns from reviews and users
    private final String SQL_SELECT_BY_BOOK_ID =
            "SELECT * FROM reviews join users on users.id = reviews.user_id WHERE reviews.book_id = ?";
    private final String SQL_COMPUTE_AVG_BY_BOOK_ID =
            "SELECT avg(score) AS average FROM reviews WHERE book_id = ?";

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

    public List<ReviewResponseDTO> findAllByBookId(String bookId){
        return jdbcTemplate.query(SQL_SELECT_BY_BOOK_ID, (rs, rowNum) -> {

            return new ReviewResponseDTO(
                    rs.getString("reviews.id"),
                    rs.getString("title"),
                    rs.getString("body"),
                    rs.getInt("score"),
                    null,
                    null,
                    null,
                    new UserInfoDTO(
                            UUID.fromString(rs.getString("users.id")),
                            rs.getString("username"),
                            null,
                            rs.getTimestamp("created_at"))

            );
        }, bookId);
    }
}
