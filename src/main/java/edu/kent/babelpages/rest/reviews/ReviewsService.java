package edu.kent.babelpages.rest.reviews;

import edu.kent.babelpages.rest.books.BooksService;
import edu.kent.babelpages.rest.reviewVotes.DTO.VoteCountDTO;
import edu.kent.babelpages.rest.reviewVotes.ReviewVotesService;
import edu.kent.babelpages.rest.reviews.DTO.ReviewCreateDTO;
import edu.kent.babelpages.rest.reviewVotes.enums.VoteType;
import edu.kent.babelpages.rest.reviews.DTO.ReviewResponseDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsService {
    private final ReviewsDAO reviewsDAO;
    private final ReviewVotesService reviewVotesService;

    public ReviewsService(ReviewsDAO reviewsDAO, ReviewVotesService reviewVotesService) {
        this.reviewsDAO = reviewsDAO;
        this.reviewVotesService = reviewVotesService;
    }

    public void saveReview(ReviewCreateDTO reviewCreateDTO){
        // we set Authentication principal as UserInfoDTO in JWTService during authentication
        // we get currently authenticated user's id here
        var user = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        reviewsDAO.save(new Review(
                null, // set on creation
                reviewCreateDTO.getBookId(),
                user.getId().toString(),
                reviewCreateDTO.getTitle(),
                reviewCreateDTO.getBody(),
                reviewCreateDTO.getScore(),
                // set on creation too
                null
        ));
    }

    public void saveVote(String reviewId, VoteType voteType){
        // explained in saveReview method
        var user = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        reviewVotesService.addVoteToReview(reviewId, user.getId().toString(), voteType);
    }

    public List<ReviewResponseDTO> getReviewsForBook(String bookId){
        // does not yet include counts for votes
        var reviewsList = reviewsDAO.findAllByBookId(bookId);

        // fetch vote counts
        reviewsList.forEach(review -> {
            VoteCountDTO votes = reviewVotesService.countVotesForReview(review.getId());

            review.setFunnyVotes(votes.getFunnyCount());
            review.setPoeticVotes(votes.getPoeticCount());
            review.setUsefulVotes(votes.getUsefulCount());

        });

        return reviewsList;
    }
}
