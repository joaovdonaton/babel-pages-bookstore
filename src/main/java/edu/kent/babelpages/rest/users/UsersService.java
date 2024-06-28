package edu.kent.babelpages.rest.users;

import edu.kent.babelpages.rest.users.DTO.UserCreationDTO;
import edu.kent.babelpages.rest.users.DTO.UserInfoDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersDAO usersDAO;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersDAO usersDAO, PasswordEncoder passwordEncoder) {
        this.usersDAO = usersDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public UserInfoDTO createUser(UserCreationDTO userCreationDTO){
        // hash password
        userCreationDTO.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));

        User u = new User(userCreationDTO);
        usersDAO.createUser(u);

        return new UserInfoDTO(u.getId(), u.getUsername());
    }
}
