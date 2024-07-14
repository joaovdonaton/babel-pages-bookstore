package edu.kent.babelpages.rest.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.kent.babelpages.rest.users.DTO.UserCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID id;
    private String username;
    @JsonIgnore
    private String passwordHash;
    private String firstName;
    private String lastName;
    private Timestamp created_at;
    private String ROLE;

    public User(UserCreationDTO userCreationDTO){
        this.username = userCreationDTO.getUsername();
        this.firstName = userCreationDTO.getFirstName();
        this.lastName = userCreationDTO.getLastName();
        this.passwordHash = userCreationDTO.getPassword();
        this.ROLE = "USER";
    }
}
