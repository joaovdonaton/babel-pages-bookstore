package edu.kent.babelpages.rest.books;

import edu.kent.babelpages.rest.books.DTO.BookRegisterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * As specified in the DAO for book, authors must be a comma separated string with author names
 */
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
    private String authors;

    public Book(BookRegisterDTO bookRegisterDTO){
        this.title = bookRegisterDTO.getTitle();
        this.description = bookRegisterDTO.getDescription();
        this.ISBN = bookRegisterDTO.getISBN();
        this.language = bookRegisterDTO.getLanguage();
        this.coverUrl = bookRegisterDTO.getCoverUrl();
        this.price = bookRegisterDTO.getPrice();
        this.stockQuantity = bookRegisterDTO.getStockQuantity();
        this.pubYear = bookRegisterDTO.getPubYear();
        this.pubMonth = bookRegisterDTO.getPubMonth();
        this.pubDay = bookRegisterDTO.getPubDay();
        this.authors = String.join(",", bookRegisterDTO.getAuthors());
    }
}
