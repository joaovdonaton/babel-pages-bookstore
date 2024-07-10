package edu.kent.babelpages.rest.books.DTO;

import edu.kent.babelpages.rest.books.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
    private Date publicationDate;
    private Set<String> authors;

    public BookSearchResultDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.coverURL = book.getCoverUrl();
        this.publicationDate = book.getPublicationDate();
        this.authors = book.getAuthors();
    }
}
