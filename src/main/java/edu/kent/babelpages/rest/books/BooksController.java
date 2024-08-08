package edu.kent.babelpages.rest.books;

import edu.kent.babelpages.rest.books.DTO.BookDetailsDTO;
import edu.kent.babelpages.rest.books.DTO.BookRegisterDTO;
import edu.kent.babelpages.rest.books.DTO.BookSearchResultDTO;
import edu.kent.babelpages.rest.books.enums.AscDescEnum;
import edu.kent.babelpages.rest.books.enums.BookOrderByEnum;
import edu.kent.babelpages.rest.books.validators.TagSetConstraint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name = "Books")
    @Operation(
            summary = "Search for books based on parameters",
            description = "Returns a JSON array with fields specified in BookSearchResultDTO"
    )
    public List<BookSearchResultDTO> getBooks(@RequestParam(required = false) String keyword,
                                              @RequestParam(defaultValue = "10", required = false) @Min(1) int limit,
                                              @RequestParam(defaultValue = "0", required = false) @Min(0) int page,
                                              @RequestParam(defaultValue = "asc", required = false) AscDescEnum ascDesc,
                                              @RequestParam(defaultValue = "title") BookOrderByEnum orderBy,
                                              @RequestParam(required = false) @Valid @TagSetConstraint Set<String> tags) {
        return booksService.search(keyword, limit, page, ascDesc, orderBy, tags);
    }

    @GetMapping("/{id}")
    @Tag(name = "Books")
    @Operation(
            summary = "Get full details of a book from id"
    )
    public BookDetailsDTO getBookDetails(@PathVariable String id){
        return booksService.getDetailsFromUUID(id);
    }

    @PostMapping("/")
    @Tag(name = "Books")
    @Operation(
            summary = "Add new book.",
            description = "Check BookRegisterDTO for non-nullable fields"
    )
    @SecurityRequirement(name = "auth")
    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@Valid @RequestBody BookRegisterDTO bookRegisterDTO){
        booksService.addBook(bookRegisterDTO);
    }

    @DeleteMapping("/{id}")
    @Tag(name = "Books")
    @Operation(
            summary = "Delete book by id."
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String id){
        booksService.deleteBook(id);
    }

    @GetMapping("/random")
    @Tag(name = "Books")
    @Operation(
            summary = "Return random book."
    )
    public BookSearchResultDTO getRandomBook(){
        return booksService.getRandom();
    }
}
