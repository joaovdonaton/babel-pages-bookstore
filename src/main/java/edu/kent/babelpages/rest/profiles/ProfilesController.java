package edu.kent.babelpages.rest.profiles;

import edu.kent.babelpages.rest.profiles.DTO.ProfileUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users/")
public class ProfilesController {
    private final ProfilesService profilesService;

    public ProfilesController(ProfilesService profilesService) {
        this.profilesService = profilesService;
    }

    /**
     * The RequestPart annotations allows us to get different parts of a multipart Request.
     * In this case, our JSON data and our image.
     */
    @PatchMapping(value = "/self",
            consumes = {"multipart/form-data"} //
    )
    @RolesAllowed("USER")
    @SecurityRequirement(name = "auth")
    @Tag(name = "Profiles")
    @Operation(
            summary = "Update fields for currently authenticated user's profile."
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@RequestPart(required = false) ProfileUpdateDTO profileUpdateDTO,
                              @RequestPart(required = false) MultipartFile file){
        profilesService.updateProfile(profileUpdateDTO, file);
    }
}
