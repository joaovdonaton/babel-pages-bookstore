package edu.kent.babelpages.rest.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private UUID id;
    private UUID userId;
    private String profilePictureUrl;
    private String country;
    private String bio;
    private String occupation;
}
