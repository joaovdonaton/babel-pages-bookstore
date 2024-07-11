package edu.kent.babelpages.rest.books.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRegisterDTO {
    @NotBlank
    @Max(150)
    private String title;
    @Max(1000)
    private String description;
    @ISBN
    private String ISBN;
    @Max(30)
    private String language;
    @Max(300)
    private String coverUrl;
    @Min(0)
    private BigDecimal price;
    @Min(0)
    private int stockQuantity;
    private Integer pubYear;
    private Integer pubMonth;
    private Integer pubDay;
    private Set<String> authors;
}
