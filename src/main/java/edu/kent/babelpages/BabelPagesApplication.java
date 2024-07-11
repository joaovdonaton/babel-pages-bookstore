package edu.kent.babelpages;

import edu.kent.babelpages.lib.security.SecurityProperties;
import edu.kent.babelpages.rest.users.DTO.UserCredentialsDTO;
import edu.kent.babelpages.rest.users.User;
import edu.kent.babelpages.rest.users.UsersDAO;
import edu.kent.babelpages.rest.users.UsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BabelPagesApplication implements CommandLineRunner {
    private final UsersDAO usersDAO;
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;

    public BabelPagesApplication(UsersDAO usersDAO, UsersService usersService, PasswordEncoder passwordEncoder,
                                 SecurityProperties securityProperties) {
        this.usersDAO = usersDAO;
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.securityProperties = securityProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(BabelPagesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(securityProperties.isDevMode()){
            // insert development admin user
            if(usersDAO.findByUsername(securityProperties.getDevAdminUsername()) == null) {
                usersDAO.createUser(new User(null,
                        securityProperties.getDevAdminUsername(),
                        passwordEncoder.encode(securityProperties.getDevAdminPassword()),
                        "John",
                        "The Admin",
                        null,
                        "ADMIN"));
            }

            // put jwt token for admin in log for easier debugging and testing
            System.out.println(usersService.login(
                    new UserCredentialsDTO(
                            securityProperties.getDevAdminUsername(), securityProperties.getDevAdminPassword())));
        }
    }
}
