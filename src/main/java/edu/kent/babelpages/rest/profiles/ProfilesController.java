package edu.kent.babelpages.rest.profiles;

import edu.kent.babelpages.rest.profiles.DTO.ProfileUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} //
    )
    @RolesAllowed("USER")
    @SecurityRequirement(name = "auth")
    @Tag(name = "Profiles")
    @Operation(
            summary = "Update fields for currently authenticated user's profile.",
            description = "Will only update non null fields. Null fields remain the same."
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(
            @RequestPart(required = false, value = "profile", name = "profile")
            @Parameter(description = "Profile update data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileUpdateDTO.class)))
            @Valid ProfileUpdateDTO profileUpdateDTO,

            @RequestPart(required = false, value = "file", name = "file")
            @Parameter(
                    description = "profile picture file",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)
            )
            MultipartFile file){
        if(profileUpdateDTO == null) profileUpdateDTO = new ProfileUpdateDTO(); // all fields set to null
        profilesService.updateProfile(profileUpdateDTO, file);
    }
}
