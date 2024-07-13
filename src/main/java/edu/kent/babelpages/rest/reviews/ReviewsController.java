package edu.kent.babelpages.rest.reviews;

import edu.kent.babelpages.rest.reviews.DTO.ReviewCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {
    private final ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @SecurityRequirement(name = "auth")
    @RolesAllowed("USER")
    @PostMapping("/")
    @Tag(name = "Reviews")
    @Operation(
            summary = "Post a review to a book as currently authenticated user."
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void postReview(@Valid ReviewCreateDTO reviewCreateDTO) {
        reviewsService.saveReview(reviewCreateDTO);
    }
}
