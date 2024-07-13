package edu.kent.babelpages.rest.reviews;

import edu.kent.babelpages.rest.reviews.DTO.ReviewCreateDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReviewsService {
    private final ReviewsDAO reviewsDAO;

    public ReviewsService(ReviewsDAO reviewsDAO) {
        this.reviewsDAO = reviewsDAO;
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
                null,
                null,
                null,
                null
        ));
    }
}
