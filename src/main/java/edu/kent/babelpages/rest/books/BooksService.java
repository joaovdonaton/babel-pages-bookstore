package edu.kent.babelpages.rest.books;

import edu.kent.babelpages.lib.error.apiExceptions.ResourceDoesNotExistException;
import edu.kent.babelpages.rest.books.DTO.BookDetailsDTO;
import edu.kent.babelpages.rest.books.DTO.BookRegisterDTO;
import edu.kent.babelpages.rest.books.DTO.BookSearchResultDTO;
import edu.kent.babelpages.rest.books.enums.AscDescEnum;
import edu.kent.babelpages.rest.books.enums.BookOrderByEnum;
import edu.kent.babelpages.rest.tags.TagsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class BooksService {
    private final BooksDAO booksDAO;
    private final TagsService tagsService;

    public BooksService(BooksDAO booksDAO, TagsService tagsService) {
        this.booksDAO = booksDAO;
        this.tagsService = tagsService;
    }

    public List<BookSearchResultDTO> search(String keyword, Integer limit, Integer page, AscDescEnum ascDesc,
                                            BookOrderByEnum bookOrderBy, Set<String> tagNames) {
        int offset = limit*page;

        return booksDAO.findAllOrderBy(bookOrderBy.toString(), ascDesc.toString(), keyword, tagNames, limit, offset).stream().map(BookSearchResultDTO::new).toList();
    }

    public BookDetailsDTO getDetailsFromUUID(String id){
        Book book = booksDAO.findById(id);

        if(book == null) throw new ResourceDoesNotExistException(HttpStatus.NOT_FOUND, "User with uuid " + id + " does not exist");

        return new BookDetailsDTO(book, tagsService.getTagsByBookId(id));
    }

    @Transactional
    public void addBook(BookRegisterDTO bookRegisterDTO){
        Book b = booksDAO.save(new Book(bookRegisterDTO));

        // get tag objects because we need their ids
        for (String tagName: bookRegisterDTO.getTags()){
            booksDAO.saveBookTag(b, tagsService.getTagByName(tagName));
        }
    }

    public void deleteBook(String id){
        booksDAO.deleteBookById(id);
    }

    public BookSearchResultDTO getRandom(){
        return new BookSearchResultDTO(booksDAO.findOneRandom());
    }
}
