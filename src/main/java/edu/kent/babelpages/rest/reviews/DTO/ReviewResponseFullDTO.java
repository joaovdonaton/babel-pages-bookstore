package edu.kent.babelpages.rest.reviews.DTO;

import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* ReviewResponseDTO returns reviews for a specific book
* This version returns more details, like book title
* */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseFullDTO {
    private String id;
    private String title;
    private String body;
    private int score;
    private Integer funnyVotes;
    private Integer usefulVotes;
    private Integer poeticVotes;
    private UserInfoDTO user;
    private String bookTitle;
    private String bookId;
}
