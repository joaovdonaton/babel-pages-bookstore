package edu.kent.babelpages.rest.users;

import edu.kent.babelpages.rest.users.DTO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService service;

    public UsersController(UsersService service) {
        this.service = service;
    }

    @PostMapping("/")
    @Tag(name = "Users")
    @Operation(
            summary = "Create a new user account"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfoDTO createUser(@RequestBody @Valid UserCreationDTO userCreationDTO) {
        return service.createUser(userCreationDTO);
    }

    @PostMapping("/login")
    @Tag(name = "Users")
    @Operation(
            summary = "Returns a JWT token."
    )
    public TokenResponseDTO login(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO){
        return new TokenResponseDTO(service.login(userCredentialsDTO));
    }

    @GetMapping("/self")
    @RolesAllowed("USER")
    @SecurityRequirement(name = "auth")
    @Tag(name = "Users")
    @Operation(
            summary = "Get currently authenticated user details from JWT token."
    )
    public UserInfoAndProfileDTO getSelf(){
        var u = (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.getInfoAndProfileById(u.getId().toString());
    }

    @GetMapping("/id/{id}")
    @Tag(name = "Users")
    @Operation(
            summary = "Get details and profile for user from id."
    )
    public UserInfoAndProfileDTO getDetails(@PathVariable String id){
        return service.getInfoAndProfileById(id);
    }

    @GetMapping("/{username}")
    @Tag(name = "Users")
    @Operation(
            summary = "Get details and profile for user from username."
    )
    public UserInfoAndProfileDTO getUser(@PathVariable String username){
        return service.getInfoAndProfileByUsername(username);
    }

}
