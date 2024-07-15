package edu.kent.babelpages.rest.users;

import edu.kent.babelpages.lib.error.apiExceptions.InvalidCredentialsException;
import edu.kent.babelpages.lib.error.apiExceptions.ResourceAlreadyExistsException;
import edu.kent.babelpages.lib.security.JWTService;
import edu.kent.babelpages.rest.profiles.ProfilesService;
import edu.kent.babelpages.rest.users.DTO.UserCreationDTO;
import edu.kent.babelpages.rest.users.DTO.UserCredentialsDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService {
    private final UsersDAO usersDAO;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final ProfilesService profilesService;

    public UsersService(UsersDAO usersDAO, PasswordEncoder passwordEncoder, JWTService jwtService, ProfilesService profilesService) {
        this.usersDAO = usersDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.profilesService = profilesService;
    }

    @Transactional
    public UserInfoDTO createUser(UserCreationDTO userCreationDTO){
        // check if user already exists
        User u = usersDAO.findByUsername(userCreationDTO.getUsername());
        if(u != null) throw new ResourceAlreadyExistsException(HttpStatus.BAD_REQUEST, "Username already exists");

        // hash password
        userCreationDTO.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));

        u = new User(userCreationDTO);
        u = usersDAO.save(u);

        profilesService.createForUser(u);

        return new UserInfoDTO(u.getId(), u.getUsername(), u.getROLE(), u.getCreated_at());
    }

    public String login(UserCredentialsDTO userCredentialsDTO){
        User u = usersDAO.findByUsername(userCredentialsDTO.getUsername());

        // We could return ResourceDoesNotExistException in case username is not found, but that would allow
        // enumeration of usernames
        if(u == null) throw new InvalidCredentialsException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");

        if(!passwordEncoder.matches(userCredentialsDTO.getPassword(), u.getPasswordHash())){
            throw new InvalidCredentialsException(HttpStatus.UNAUTHORIZED,
                    "Invalid Credentials");
        }

        return jwtService.createToken(new UserInfoDTO(
                u.getId(),
                u.getUsername(),
                u.getROLE(),
                u.getCreated_at()
        ));
    }
}
