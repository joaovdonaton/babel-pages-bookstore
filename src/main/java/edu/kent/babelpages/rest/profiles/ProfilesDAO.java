package edu.kent.babelpages.rest.profiles;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ProfilesDAO {
    private final String SQL_SELECT_BY_USER_ID =
            "SELECT * FROM profiles WHERE user_id=?";
    private final String SQL_INSERT_EMPTY_PROFILE =
            "INSERT INTO profiles (id, user_id, profile_picture_url, country, bio, occupation) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

    private final JdbcTemplate jdbcTemplate;

    public ProfilesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Profile findByUserId(String userId) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_USER_ID, new ProfileRowMapper(), userId);
    }

    public Profile save(Profile profile) {
        String uuidStr = UUID.randomUUID().toString();

        jdbcTemplate.update(SQL_INSERT_EMPTY_PROFILE,
                uuidStr,
                profile.getUserId().toString(),
                profile.getProfilePictureUrl(),
                profile.getCountry(),
                profile.getBio(),
                profile.getOccupation());

        return findByUserId(profile.getUserId().toString());
    }

    public void update(Profile profile){

    }
}
