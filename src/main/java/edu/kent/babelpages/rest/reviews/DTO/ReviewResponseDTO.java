package edu.kent.babelpages.rest.reviews.DTO;

import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import edu.kent.babelpages.rest.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {
    private String id;
    private String title;
    private String body;
    private int score;
    private Integer funnyVotes;
    private Integer usefulVotes;
    private Integer poeticVotes;
    private UserInfoDTO user;
}
