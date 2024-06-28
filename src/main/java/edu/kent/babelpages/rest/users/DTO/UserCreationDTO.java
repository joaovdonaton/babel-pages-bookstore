package edu.kent.babelpages.rest.users.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreationDTO {
    @NotBlank
    @Size(min = 6, max = 30)
    private String username;
    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;
    @NotBlank
    @Size(min = 8, max = 30)
    private String password;
}
