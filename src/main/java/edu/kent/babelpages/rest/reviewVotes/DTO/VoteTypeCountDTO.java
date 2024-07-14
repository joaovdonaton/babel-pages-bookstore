package edu.kent.babelpages.rest.reviewVotes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteTypeCountDTO {
    private String type;
    private int count;
}
