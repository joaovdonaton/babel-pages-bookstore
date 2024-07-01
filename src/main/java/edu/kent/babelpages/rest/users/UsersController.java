package edu.kent.babelpages.rest.users;

import edu.kent.babelpages.rest.users.DTO.TokenResponseDTO;
import edu.kent.babelpages.rest.users.DTO.UserCreationDTO;
import edu.kent.babelpages.rest.users.DTO.UserCredentialsDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO){
        return new TokenResponseDTO(service.login(userCredentialsDTO));
    }

    @GetMapping("/forbidden")
    @RolesAllowed("USER")
    @SecurityRequirement(name = "auth")
    public UserInfoDTO getForbidden(){
        return (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
