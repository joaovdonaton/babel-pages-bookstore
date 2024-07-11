package edu.kent.babelpages.rest.books;

import edu.kent.babelpages.lib.error.apiExceptions.ResourceDoesNotExistException;
import edu.kent.babelpages.rest.books.DTO.BookDetailsDTO;
import edu.kent.babelpages.rest.books.DTO.BookSearchResultDTO;
import edu.kent.babelpages.rest.books.enums.AscDescEnum;
import edu.kent.babelpages.rest.books.enums.BookOrderByEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BooksService {
    private final BooksDAO booksDAO;

    public BooksService(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    public List<BookSearchResultDTO> search(String keyword, Integer limit, Integer page, AscDescEnum ascDesc,
                                            BookOrderByEnum bookOrderBy) {
        int offset = limit*page;

        if(keyword == null)
            return booksDAO.findAllOrderBy(bookOrderBy.toString(), limit, offset).stream().map(BookSearchResultDTO::new).toList();
        else
            return booksDAO.findAllOrderByWithKeyword(keyword, bookOrderBy.toString(), limit, offset).stream().map(BookSearchResultDTO::new).toList();
    }

    public BookDetailsDTO getDetailsFromUUID(String id){
        Book book = booksDAO.findById(id);

        if(book == null) throw new ResourceDoesNotExistException(HttpStatus.NOT_FOUND, "User with uuid " + id + " does not exist");

        return new BookDetailsDTO(book);
    }
}
