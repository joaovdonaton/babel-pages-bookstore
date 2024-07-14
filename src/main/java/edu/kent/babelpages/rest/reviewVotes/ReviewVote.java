package edu.kent.babelpages.rest.reviewVotes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewVote {
    private String reviewId;
    private String userId;
    private String type;
}
