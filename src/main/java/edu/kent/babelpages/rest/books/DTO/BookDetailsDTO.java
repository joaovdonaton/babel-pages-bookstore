package edu.kent.babelpages.rest.books.DTO;

import edu.kent.babelpages.rest.books.Book;
import edu.kent.babelpages.rest.tags.DTO.TagResultDTO;
import edu.kent.babelpages.rest.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private Set<TagResultDTO> tags;
    private BigDecimal avgScore;

    public BookDetailsDTO(Book book, Set<Tag> tags) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.coverURL = book.getCoverUrl();
        this.pubYear = book.getPubYear();
        this.pubMonth = book.getPubMonth();
        this.pubDay = book.getPubDay();
        this.authors = new HashSet<>(Arrays.asList(book.getAuthors().split(",")));
        this.ISBN = book.getISBN();
        this.language = book.getLanguage();
        this.isLowStock = book.getStockQuantity() <= 10;
        this.avgScore = book.getAvgScore();

        this.tags = tags.stream().map(TagResultDTO::new).collect(Collectors.toSet());
    }
}
