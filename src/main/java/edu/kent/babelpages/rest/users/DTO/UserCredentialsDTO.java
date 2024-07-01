package edu.kent.babelpages.rest.users.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredentialsDTO {
    private String username;
    private String password;
}
