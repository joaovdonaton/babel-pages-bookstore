package edu.kent.babelpages.rest.reviews.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateDTO {
    @UUID
    @NotNull
    private String bookId;
    @NotNull
    @Length(min = 3, max = 150)
    private String title;
    @NotNull
    @Length(min = 3, max = 2000)
    private String body;
    @Min(0)
    @Max(10)
    private int score;
}
