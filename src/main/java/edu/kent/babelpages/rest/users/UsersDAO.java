package edu.kent.babelpages.rest.users;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UsersDAO {
    private final String SQL_INSERT_USER = "INSERT INTO users (id, username, password_hash, first_name, last_name, role) VALUES(?, ?,?,?,?,?)";
    private final String SQL_SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User save(User user){
        String uuidStr = UUID.randomUUID().toString();

        jdbcTemplate.update(SQL_INSERT_USER, uuidStr, user.getUsername(), user.getPasswordHash(), user.getFirstName(), user.getLastName(), user.getROLE());

        return findByUsername(user.getUsername()); // this is fine because we know usernames are unique
    }

    public User findByUsername(String username){
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_USERNAME, new UserRowMapper(), username);
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public User findById(String id){
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID, new UserRowMapper(), id);
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}
