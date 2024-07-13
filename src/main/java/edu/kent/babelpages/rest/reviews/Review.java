package edu.kent.babelpages.rest.reviews;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String id;
    private String bookId;
    private String userId;
    private String title;
    private String body;
    private int score;
    private Integer usefulVotes;
    private Integer funnyVotes;
    private Integer poeticVotes;
    private Timestamp createdAt;
}
