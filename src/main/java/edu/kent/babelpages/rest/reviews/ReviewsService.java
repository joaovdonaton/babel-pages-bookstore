package edu.kent.babelpages.rest.reviews;

import edu.kent.babelpages.lib.error.apiExceptions.InternalServerException;
import edu.kent.babelpages.lib.error.apiExceptions.ResourceAlreadyExistsException;
import edu.kent.babelpages.rest.books.BooksService;
import edu.kent.babelpages.rest.reviewVotes.DTO.VoteCountDTO;
import edu.kent.babelpages.rest.reviewVotes.ReviewVotesService;
import edu.kent.babelpages.rest.reviews.DTO.ReviewCreateDTO;
import edu.kent.babelpages.rest.reviewVotes.enums.VoteType;
import edu.kent.babelpages.rest.reviews.DTO.ReviewResponseDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReviewsService {
    private final ReviewsDAO reviewsDAO;
    private final ReviewVotesService reviewVotesService;
    private final BooksService booksService;

    public ReviewsService(ReviewsDAO reviewsDAO, ReviewVotesService reviewVotesService, BooksService booksService) {
        this.reviewsDAO = reviewsDAO;
        this.reviewVotesService = reviewVotesService;
        this.booksService = booksService;
    }

    /**
     * Save review and also update the avg_score column for the books table
     */
    @Transactional
    public void saveReview(ReviewCreateDTO reviewCreateDTO){
        // we set Authentication principal as UserInfoDTO in JWTService during authentication
        // we get currently authenticated user's id here
        var user = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // check if user has already posted review for this book
        if(reviewsDAO.findAllByBookId(reviewCreateDTO.getBookId())
                .stream().anyMatch(b -> b.getUser().getId().equals(user.getId()))){
            throw new ResourceAlreadyExistsException(HttpStatus.CONFLICT, "User has already posted a review for this book.");
        }

        var review = reviewsDAO.save(new Review(
                null, // set on creation
                reviewCreateDTO.getBookId(),
                user.getId().toString(),
                reviewCreateDTO.getTitle(),
                reviewCreateDTO.getBody(),
                reviewCreateDTO.getScore(),
                // set on creation too
                null
        ));

        if(review == null) throw new InternalServerException("Server error while attempting to save review.");

        booksService.computeAndUpdateAverageById(review.getBookId());
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
