package edu.kent.babelpages.rest.books.DTO;

import edu.kent.babelpages.rest.books.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchResultDTO {
    private UUID id;
    private String title;
    private BigDecimal price;
    private String coverURL;
    private int pubYear;
    private int pubMonth;
    private int pubDay;
    private Set<String> authors;
    private BigDecimal avgScore;

    public BookSearchResultDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.coverURL = book.getCoverUrl();
        this.pubYear = book.getPubYear();
        this.pubMonth = book.getPubMonth();
        this.pubDay = book.getPubDay();
        this.authors = new HashSet<>(Arrays.asList(book.getAuthors().split(",")));
        this.avgScore = book.getAvgScore();
    }
}
