package edu.kent.babelpages.rest.users;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(UUID.fromString(rs.getString("id")),
                rs.getString("username"),
                rs.getString("password_hash"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getTimestamp("created_at"),
                rs.getString("ROLE")
                );
    }
}
