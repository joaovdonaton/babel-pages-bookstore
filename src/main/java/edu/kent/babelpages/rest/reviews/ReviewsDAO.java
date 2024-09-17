package edu.kent.babelpages.rest.reviews;

import edu.kent.babelpages.lib.error.apiExceptions.InternalServerException;
import edu.kent.babelpages.rest.books.enums.AscDescEnum;
import edu.kent.babelpages.rest.reviews.DTO.ReviewResponseDTO;
import edu.kent.babelpages.rest.reviews.DTO.ReviewResponseFullDTO;
import edu.kent.babelpages.rest.reviews.enums.ReviewOrderByEnum;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ReviewsDAO {
    private final String SQL_INSERT_REVIEW =
            "INSERT INTO reviews (id, book_id, user_id, score, title, body) VALUES (?, ?, ?, ?, ?, ?);";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM reviews WHERE id = ?;";

    // a little different because we want to select columns from reviews and users
    private final String SQL_SELECT_BY_BOOK_ID =
            "SELECT * FROM reviews join users on users.id = reviews.user_id WHERE reviews.book_id = ?";
    private final String SQL_COMPUTE_AVG_BY_BOOK_ID =
            "SELECT avg(score) AS average FROM reviews WHERE book_id = ?";

    /**
     * @param orderByColumn from ReviewOrderByEnum
     */
    private static String BUILD_QUERY_SEARCH(ReviewOrderByEnum orderByColumn, boolean usernameExists, AscDescEnum ascDesc, int limit, int offset){
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM reviews JOIN books ON books.id = reviews.book_id " +
                        "JOIN users ON users.id = reviews.user_id ");

        if(usernameExists){
            sql.append(" WHERE users.username = :username");
        }

        sql.append(" ORDER BY ");

        // TODO: implement the rest of these order by options
        switch(orderByColumn){
            case DATE:
                sql.append("reviews.created_at");
                break;
            case VOTE_COUNT:
                break;
            case MOST_FUNNY:
                break;
            case MOST_USEFUL:
                break;
            case MOST_POETIC:
                break;
            default:
                throw new InternalServerException("Unkown column for review orderby");
        }

        sql.append(" ").append(ascDesc.toString()).append(" ");

        sql.append(" LIMIT ").append(limit).append(" OFFSET ").append(offset);

        return sql.toString();
    }

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReviewsDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Review findById(String id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new ReviewRowMapper(), id);
    }

    public Review save(Review review) {
        String uuidString = UUID.randomUUID().toString();

        jdbcTemplate.update(SQL_INSERT_REVIEW,
                uuidString,
                review.getBookId(),
                review.getUserId(),
                review.getScore(),
                review.getTitle(),
                review.getBody());

        return findById(uuidString);
    }

    public List<ReviewResponseDTO> findAllByBookId(String bookId){
        return jdbcTemplate.query(SQL_SELECT_BY_BOOK_ID, (rs, rowNum) -> {

            return new ReviewResponseDTO(
                    rs.getString("reviews.id"),
                    rs.getString("title"),
                    rs.getString("body"),
                    rs.getInt("score"),
                    null,
                    null,
                    null,
                    new UserInfoDTO(
                            UUID.fromString(rs.getString("users.id")),
                            rs.getString("username"),
                            null,
                            rs.getTimestamp("created_at"))

            );
        }, bookId);
    }

    public List<ReviewResponseFullDTO> findAllOrderBy(ReviewOrderByEnum orderByColumn, String filterByUsername,
                                                      AscDescEnum ascDesc, int limit, int offset){
        return namedParameterJdbcTemplate.query(BUILD_QUERY_SEARCH(orderByColumn, filterByUsername != null, ascDesc, limit, offset),
                new MapSqlParameterSource().addValue("username", filterByUsername),
                (rs, rn) -> {
            return new ReviewResponseFullDTO(
                    rs.getString("reviews.id"),
                    rs.getString("reviews.title"),
                    rs.getString("body"),
                    rs.getInt("score"),
                    null,
                    null,
                    null,
                    new UserInfoDTO(
                            UUID.fromString(rs.getString("users.id")),
                            rs.getString("username"),
                            null,
                            rs.getTimestamp("created_at")),
                    rs.getString("books.title"),
                    rs.getString("books.id")
            );});
    }
}
