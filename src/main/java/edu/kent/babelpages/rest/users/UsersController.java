package edu.kent.babelpages.rest.users;

import edu.kent.babelpages.rest.users.DTO.UserCreationDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    private UsersService service;

    public UsersController(UsersService service) {
        this.service = service;
    }

    @PostMapping("/")
    public UserInfoDTO createUser(@RequestBody @Valid UserCreationDTO userCreationDTO) {
        return service.createUser(userCreationDTO);
    }

}
