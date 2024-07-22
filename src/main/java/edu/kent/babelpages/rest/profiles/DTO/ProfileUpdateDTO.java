package edu.kent.babelpages.rest.profiles.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateDTO {
    @Length(min = 50, max = 1000)
    private String bio;
    @Length(min = 3, max = 50)
    private String occupation;
    // todo: add validation here
    private String country;
}
