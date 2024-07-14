package edu.kent.babelpages.rest.reviewVotes;

import edu.kent.babelpages.lib.error.apiExceptions.ResourceAlreadyExistsException;
import edu.kent.babelpages.rest.reviewVotes.DTO.VoteCountDTO;
import edu.kent.babelpages.rest.reviewVotes.DTO.VoteTypeCountDTO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewVotesDAO {
    private final String SQL_INSERT = "INSERT INTO review_votes (review_id, user_id, type) VALUES (?, ?, ?)";
    private final String SQL_COUNT_VOTES =
            "SELECT type, count(*) as count FROM review_votes WHERE review_id = ? GROUP BY type;";

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

    public VoteCountDTO countVotesByReviewId(String id){
        List<VoteTypeCountDTO> counts = jdbcTemplate.query(SQL_COUNT_VOTES,
                (rs, n) -> new VoteTypeCountDTO(rs.getString("type"), rs.getInt("count")),
                id);

        var voteCountDTO = new VoteCountDTO();

        for(var v : counts){
            switch (v.getType()){
                case "FUNNY":
                    voteCountDTO.setFunnyCount(v.getCount());
                    break;
                case "POETIC":
                    voteCountDTO.setPoeticCount(v.getCount());
                    break;
                case "USEFUL":
                    voteCountDTO.setUsefulCount(v.getCount());
                    break;
            }
        }

        return voteCountDTO;
    }
}
