package edu.kent.babelpages.rest.users;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDAO {
    private final String SQL_INSERT_USER = "INSERT INTO users (username, password_hash, first_name, last_name, role) VALUES(?,?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user){
        jdbcTemplate.update(SQL_INSERT_USER, user.getUsername(), user.getPasswordHash(), user.getFirstName(), user.getLastName(), user.getROLE());
    }
}
