package edu.kent.babelpages.rest.users.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreationDTO {
    @NotBlank
    @Size(min = 6, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Field must only contain letters and numbers")
    private String username;
    @NotBlank
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Field must only contain letters and spaces")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Field must only contain letters and spaces")
    private String lastName;
    @NotBlank
    @Size(min = 8, max = 128)
    private String password;
}
