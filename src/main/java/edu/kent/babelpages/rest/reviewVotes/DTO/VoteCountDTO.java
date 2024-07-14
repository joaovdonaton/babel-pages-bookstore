package edu.kent.babelpages.rest.reviewVotes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * maps to each row returned by count group by of vote type
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteCountDTO {
    private int funnyCount;
    private int poeticCount;
    private int usefulCount;
}
