package edu.kent.babelpages.rest.books;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Book {
    private UUID id;
    private String title;
    private String description;
    private String ISBN;
    private String language;
    private BigDecimal price;
    private int stock_quantity;
    private Date publicationDate;
    private Set<String> authors;
}
