package edu.kent.babelpages.rest.users;

import edu.kent.babelpages.rest.users.DTO.TokenResponseDTO;
import edu.kent.babelpages.rest.users.DTO.UserCreationDTO;
import edu.kent.babelpages.rest.users.DTO.UserCredentialsDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
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
    private UsersService service;

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
    public UserInfoDTO getForbidden(){
        return (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
