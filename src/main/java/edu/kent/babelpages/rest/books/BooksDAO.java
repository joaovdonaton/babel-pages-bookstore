package edu.kent.babelpages.rest.books;

import edu.kent.babelpages.lib.error.apiExceptions.ResourceDoesNotExistException;
import edu.kent.babelpages.rest.tags.Tag;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static edu.kent.babelpages.rest.books.enums.BookOrderByEnum.PUBLICATION_DATE;

/**
 * Important note on how Books are stored:
 * We use a comma separated string for multiple authors
 */
@Repository
public class BooksDAO {
    private static String BUILD_QUERY_SELECT_SEARCH(String orderByColumn, String ascDesc, String keyword, Set<String> tagNames) {
        StringBuilder sql = new StringBuilder(
                // we only select columns from books here since we are only interested in that for now
                "SELECT books.* FROM books_tags JOIN tags ON tags.id = books_tags.tag_id JOIN books ON books.id = books_tags.book_id "
        );

        // logic to build WHERE clause
        if(keyword != null || tagNames != null) sql.append(" WHERE ");

        if(keyword != null){
            sql.append(" ( title LIKE :keyword OR authors LIKE :keyword )");
        }

        if(tagNames != null){
            if(keyword != null) sql.append(" AND ");

            sql.append("( ");
            for(int i = 0; i < tagNames.size(); i++){
                if(i != 0) sql.append(" OR ");
                sql.append(" tags.name = :tagName").append(i);
            }
            sql.append(" )");
        }

        // we have to group by book id otherwise we have repeated books because of the join with books_tags
        sql.append(" GROUP BY books.id");

        if(orderByColumn != null){
            sql.append(" ORDER BY ");
            if(!orderByColumn.equals(PUBLICATION_DATE.toString())) sql.append(orderByColumn);
            /*
            We need a special case for publication_date order by, since we stored year, month and day in different columns
             */
            else{
                sql.append("pub_year ").append(ascDesc).append(", ");
                sql.append("pub_month ").append(ascDesc).append(", ");
                sql.append("pub_day ").append(ascDesc);
            }
        }

        sql.append(" LIMIT :limit OFFSET :offset");

        return sql.toString();
    }

    private final String SQL_SELECT_BY_ID = "SELECT * FROM books WHERE id = ?";
    private final String SQL_INSERT_BOOK = "INSERT INTO books " +
            "(id, title, description, isbn, language, price, stock_quantity, authors, cover_url, pub_year, pub_month, pub_day)" +
            " VALUES (?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?)";
    private final String SQL_INSERT_BOOK_TAG = "INSERT INTO books_tags (book_id, tag_id) VALUES (:book_id, :tag_id)";
    private final String SQL_DELETE_BY_ID = "DELETE FROM books WHERE id = ?";
    private final String SQL_SELECT_RANDOM = "SELECT * FROM books ORDER BY RAND() LIMIT 1";
    private final String SQL_UPDATE_AVERAGE =
            "UPDATE books SET avg_score = (SELECT avg(score) FROM reviews WHERE book_id = :id) WHERE id = :id";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BooksDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Book> findAllOrderBy(String orderBy, String ascDesc, String keyword, Set<String> tagNames, int limit, int offset){
        // add wildcards to keyword for better search
        if(keyword != null) keyword = '%' + keyword.trim() + '%';

        var parameters = new MapSqlParameterSource()
                .addValue("keyword", keyword)
                .addValue("limit", limit)
                .addValue("offset", offset);

        // add tags to parameter, should be named tagNameN where N is index starting at 0 for each tag
        if(tagNames != null) {
            var tagNamesList = tagNames.stream().toList();
            for (int i = 0; i < tagNamesList.size(); i++) {
                parameters.addValue("tagName" + i, tagNamesList.get(i));
            }
        }

        return namedParameterJdbcTemplate.query(BUILD_QUERY_SELECT_SEARCH(orderBy, ascDesc, keyword, tagNames),
                parameters,
                new BookRowMapper());
    }

    public Book findById(String id){
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new BookRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    /**
     * @param book id field not required, will be generated by method
     */
    public Book save(Book book){
        String UUIDstr = UUID.randomUUID().toString();

        jdbcTemplate.update(SQL_INSERT_BOOK,
                UUIDstr,
                book.getTitle(),
                book.getDescription(),
                book.getISBN(),
                book.getLanguage(),
                book.getPrice(),
                book.getStockQuantity(),
                book.getAuthors(),
                book.getCoverUrl(),
                book.getPubYear(),
                book.getPubMonth(),
                book.getPubDay()
                );

        return findById(UUIDstr);
    }

    /**
     * adds tag to book
     * @param book needs id field
     * @param tag needs id field
     */
    public void saveBookTag(Book book, Tag tag){
        namedParameterJdbcTemplate.update(SQL_INSERT_BOOK_TAG, new MapSqlParameterSource()
                .addValue("book_id", book.getId().toString())
                .addValue("tag_id", tag.getId()));
    }

    public void deleteBookById(String id){
        int n = jdbcTemplate.update(SQL_DELETE_BY_ID, id);
        if(n == 0){ // i.e delete failed
            throw new ResourceDoesNotExistException(HttpStatus.NOT_FOUND, "Book with id " + id + " does not exist.");
        }
    }

    public Book findOneRandom(){
        return jdbcTemplate.queryForObject(SQL_SELECT_RANDOM, new BookRowMapper());
    }

    public void updateAverageScoreByBookId(String id){
        namedParameterJdbcTemplate.update(SQL_UPDATE_AVERAGE, new MapSqlParameterSource("id", id));
    }
}
