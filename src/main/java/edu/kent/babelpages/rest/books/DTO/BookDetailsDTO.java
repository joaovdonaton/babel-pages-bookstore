package edu.kent.babelpages.rest.books.DTO;

import edu.kent.babelpages.rest.books.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailsDTO {
    private UUID id;
    private String title;
    private BigDecimal price;
    private String coverURL;
    private String ISBN;
    private String description;
    private String language;
    private int pubYear;
    private int pubMonth;
    private int pubDay;
    private Set<String> authors;
    private boolean isLowStock;

    public BookDetailsDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.coverURL = book.getCoverUrl();
        this.pubYear = book.getPubYear();
        this.pubMonth = book.getPubMonth();
        this.pubDay = book.getPubDay();
        this.authors = book.getAuthors();
        this.ISBN = book.getISBN();
        this.language = book.getLanguage();
        this.isLowStock = book.getStockQuantity() <= 10;
    }
}
