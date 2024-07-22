package edu.kent.babelpages.rest.users.DTO;

import edu.kent.babelpages.rest.profiles.DTO.ProfileInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndProfileDTO {
    private UUID id;
    private String username;
    private String role;
    private Timestamp createdAt;
    private ProfileInfoDTO profile;
}
