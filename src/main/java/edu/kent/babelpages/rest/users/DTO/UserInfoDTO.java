package edu.kent.babelpages.rest.users.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserInfoDTO {
    private UUID id;
    private String username;
}
