package edu.kent.babelpages.rest.books;

import edu.kent.babelpages.rest.books.DTO.BookSearchResultDTO;
import edu.kent.babelpages.rest.books.enums.AscDescEnum;
import edu.kent.babelpages.rest.books.enums.BookOrderByEnum;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public List<BookSearchResultDTO> getBooks(@RequestParam(required = false) String keyword,
                                              @RequestParam(defaultValue = "10", required = false) int limit,
                                              @RequestParam(defaultValue = "0", required = false) int page,
                                              @RequestParam(defaultValue = "asc", required = false) AscDescEnum ascDesc,
                                              @RequestParam(defaultValue = "title", required = false) BookOrderByEnum orderBy) {
        return booksService.search(keyword, limit, page, ascDesc, orderBy);
    }
}
