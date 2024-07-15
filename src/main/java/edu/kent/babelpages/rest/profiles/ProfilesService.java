package edu.kent.babelpages.rest.profiles;

import edu.kent.babelpages.rest.users.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfilesService {
    private final ProfilesDAO profilesDAO;

    public ProfilesService(ProfilesDAO profilesDAO) {
        this.profilesDAO = profilesDAO;
    }

    public Profile getProfileFromUserId(UUID id) {
        return profilesDAO.findByUserId(id.toString());
    }

    /**
     * create a blank profile for user on user creation.
     * fields can be filled in later on another endpoint.
     */
    public Profile createForUser(User u){
        return profilesDAO.save(new Profile(null, u.getId(), null, null, null, null));
    }
}
