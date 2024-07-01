package edu.kent.babelpages.rest.users;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDAO {
    private final String SQL_INSERT_USER = "INSERT INTO users (username, password_hash, first_name, last_name, role) VALUES(?,?,?,?,?)";
    private final String SQL_SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";

    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user){
        jdbcTemplate.update(SQL_INSERT_USER, user.getUsername(), user.getPasswordHash(), user.getFirstName(), user.getLastName(), user.getROLE());
    }

    public User findByUsername(String username){
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_USERNAME, new UserRowMapper(), username);
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}
