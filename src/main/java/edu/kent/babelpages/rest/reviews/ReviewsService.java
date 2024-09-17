package edu.kent.babelpages.rest.reviews;

import edu.kent.babelpages.lib.error.apiExceptions.InternalServerException;
import edu.kent.babelpages.lib.error.apiExceptions.ResourceAlreadyExistsException;
import edu.kent.babelpages.lib.error.apiExceptions.ResourceDoesNotExistException;
import edu.kent.babelpages.rest.books.BooksService;
import edu.kent.babelpages.rest.books.enums.AscDescEnum;
import edu.kent.babelpages.rest.reviewVotes.DTO.VoteCountDTO;
import edu.kent.babelpages.rest.reviewVotes.ReviewVotesService;
import edu.kent.babelpages.rest.reviews.DTO.ReviewCreateDTO;
import edu.kent.babelpages.rest.reviewVotes.enums.VoteType;
import edu.kent.babelpages.rest.reviews.DTO.ReviewResponseDTO;
import edu.kent.babelpages.rest.reviews.DTO.ReviewResponseFullDTO;
import edu.kent.babelpages.rest.reviews.enums.ReviewOrderByEnum;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import edu.kent.babelpages.rest.users.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.apache.commons.lang3.BooleanUtils.forEach;

@Service
public class ReviewsService {
    private final ReviewsDAO reviewsDAO;
    private final ReviewVotesService reviewVotesService;
    private final BooksService booksService;
    private final UsersService usersService;

    public ReviewsService(ReviewsDAO reviewsDAO, ReviewVotesService reviewVotesService, BooksService booksService, UsersService usersService) {
        this.reviewsDAO = reviewsDAO;
        this.reviewVotesService = reviewVotesService;
        this.booksService = booksService;
        this.usersService = usersService;
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

    public List<ReviewResponseFullDTO> search(ReviewOrderByEnum orderBy, String filterByUsername, AscDescEnum ascDesc, int limit, int page) {
        int offset = limit*page;

        if(filterByUsername != null && !usersService.existsByUsername(filterByUsername)) throw new ResourceDoesNotExistException(HttpStatus.NOT_FOUND,
                "User with username " + filterByUsername + "does not exist");

        var reviews = reviewsDAO.findAllOrderBy(orderBy, filterByUsername, ascDesc, limit, offset);

        // todo: remove this repetition
        reviews.forEach(review -> {
            VoteCountDTO votes = reviewVotesService.countVotesForReview(review.getId());

            review.setFunnyVotes(votes.getFunnyCount());
            review.setPoeticVotes(votes.getPoeticCount());
            review.setUsefulVotes(votes.getUsefulCount());
        });

        return reviews;
    }
}
