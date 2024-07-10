package edu.kent.babelpages.rest.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private UUID id;
    private String title;
    private String description;
    private String ISBN;
    private String language;
    private String coverUrl;
    private BigDecimal price;
    private int stockQuantity;
    private Integer pubYear;
    private Integer pubMonth;
    private Integer pubDay;
    private Set<String> authors;
}
