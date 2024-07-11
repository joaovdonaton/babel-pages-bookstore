package edu.kent.babelpages.rest.books.DTO;

import edu.kent.babelpages.rest.books.validators.TagSetConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRegisterDTO {
    @NotBlank
    @Length(max = 150)
    private String title;
    @Length(max = 1000)
    private String description;
    @ISBN
    private String ISBN;
    @Length(max = 30)
    private String language;
    @Length(max = 300)
    private String coverUrl;
    @Min(0)
    private BigDecimal price;
    @Min(0)
    @NotNull
    private int stockQuantity;
    private Integer pubYear;
    private Integer pubMonth;
    private Integer pubDay;
    private Set<String> authors;
    @TagSetConstraint
    private Set<String> tags;
}
