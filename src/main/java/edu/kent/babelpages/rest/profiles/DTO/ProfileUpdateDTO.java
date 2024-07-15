package edu.kent.babelpages.rest.profiles.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileUpdateDTO {
    private String bio;
    private String occupation;
    // todo: add validation here
    private String country;
}
