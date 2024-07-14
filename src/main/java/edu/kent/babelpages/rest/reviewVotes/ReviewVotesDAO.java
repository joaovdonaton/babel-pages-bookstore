package edu.kent.babelpages.rest.reviewVotes;

import edu.kent.babelpages.lib.error.apiExceptions.ResourceAlreadyExistsException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;

@Repository
public class ReviewVotesDAO {
    private final String SQL_INSERT = "INSERT INTO review_votes (review_id, user_id, type) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public ReviewVotesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ReviewVote reviewVote){
        try {
            jdbcTemplate.update(SQL_INSERT, reviewVote.getReviewId(), reviewVote.getUserId(), reviewVote.getType());
        }
        catch(DuplicateKeyException duplicateKeyException){
            throw new ResourceAlreadyExistsException(HttpStatus.CONFLICT, "Resource already exists.");
        }
    }
}
