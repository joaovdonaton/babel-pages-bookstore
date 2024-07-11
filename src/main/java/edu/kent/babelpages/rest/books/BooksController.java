package edu.kent.babelpages.rest.books;

import edu.kent.babelpages.rest.books.DTO.BookDetailsDTO;
import edu.kent.babelpages.rest.books.DTO.BookSearchResultDTO;
import edu.kent.babelpages.rest.books.enums.AscDescEnum;
import edu.kent.babelpages.rest.books.enums.BookOrderByEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
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
    @Tag(name = "Books")
    @Operation(
            summary = "Search for books based on parameters",
            description = "Returns a JSON array with fields specified in BookSearchResultDTO"
    )
    public List<BookSearchResultDTO> getBooks(@RequestParam(required = false) String keyword,
                                              @RequestParam(defaultValue = "10", required = false) @Min(1) int limit,
                                              @RequestParam(defaultValue = "0", required = false) @Min(0) int page,
                                              @RequestParam(defaultValue = "asc", required = false) AscDescEnum ascDesc,
                                              @RequestParam(defaultValue = "title", required = false) BookOrderByEnum orderBy) {
        return booksService.search(keyword, limit, page, ascDesc, orderBy);
    }

    @GetMapping("/{id}")
    @Tag(name = "Books")
    @Operation(
            summary = "Get full details of a book from id"
    )
    public BookDetailsDTO getBookDetails(@PathVariable String id){
        return booksService.getDetailsFromUUID(id);
    }
}
