package edu.kent.babelpages.rest.profiles.DTO;

import edu.kent.babelpages.rest.profiles.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfoDTO {
    private String profilePictureUrl;
    private String country;
    private String bio;
    private String occupation;

    public ProfileInfoDTO(Profile profile){
        this.profilePictureUrl = profile.getProfilePictureUrl();
        this.country = profile.getCountry();
        this.bio = profile.getBio();
        this.occupation = profile.getOccupation();
    }
}
