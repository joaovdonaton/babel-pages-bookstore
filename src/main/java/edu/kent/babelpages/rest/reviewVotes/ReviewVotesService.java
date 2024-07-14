package edu.kent.babelpages.rest.reviewVotes;

import edu.kent.babelpages.rest.reviewVotes.DTO.VoteCountDTO;
import edu.kent.babelpages.rest.reviewVotes.enums.VoteType;
import org.springframework.stereotype.Service;

@Service
public class ReviewVotesService {
    private final ReviewVotesDAO reviewVotesDAO;

    public ReviewVotesService(ReviewVotesDAO reviewVotesDAO) {
        this.reviewVotesDAO = reviewVotesDAO;
    }

    public void addVoteToReview(String reviewId, String userId, VoteType voteType){
        reviewVotesDAO.save(new ReviewVote(reviewId, userId, voteType.toString()));
    }

    public VoteCountDTO countVotesForReview(String reviewId){
        return reviewVotesDAO.countVotesByReviewId(reviewId);
    }
}
