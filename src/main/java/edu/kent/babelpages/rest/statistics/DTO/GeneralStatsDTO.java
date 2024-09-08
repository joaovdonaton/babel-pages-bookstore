package edu.kent.babelpages.rest.statistics.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralStatsDTO {
    int reviewCount;
    int usersCount;
    int booksCount;
    String commonGenre;
}
