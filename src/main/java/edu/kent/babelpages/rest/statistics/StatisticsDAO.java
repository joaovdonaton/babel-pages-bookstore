package edu.kent.babelpages.rest.statistics;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatisticsDAO {
    private String BUILD_SQL_COUNT_ROWS(String table){
        return "SELECT count(*) as count FROM " + table;
    }

    private final String SQL_MOST_COMMON_GENRE =
            "SELECT t.name, count(*) as num FROM books b JOIN books_tags bt ON bt.book_id = b.id " +
                    "JOIN tags t ON bt.tag_id = t.id AND t.type = \"GENRE\" GROUP BY name ORDER BY num DESC LIMIT 1;";

    private final JdbcTemplate jdbcTemplate;

    public StatisticsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int countReviews(){
        return jdbcTemplate.query(BUILD_SQL_COUNT_ROWS("reviews"), (rs, rn) -> {
            return rs.getInt("count");
        }).get(0);
    }

    public int countUsers(){
        return jdbcTemplate.query(BUILD_SQL_COUNT_ROWS("users"), (rs, rn) -> {
            return rs.getInt("count");
        }).get(0);
    }

    public int countBooks(){
        return jdbcTemplate.query(BUILD_SQL_COUNT_ROWS("books"), (rs, rn) -> {
            return rs.getInt("count");
        }).get(0);
    }

    public String computeMostCommonGenre(){
        return jdbcTemplate.queryForObject(SQL_MOST_COMMON_GENRE, (rs, rn) -> {
            return rs.getString("name");
        });
    }
}
