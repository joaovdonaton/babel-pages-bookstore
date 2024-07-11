package edu.kent.babelpages;

import edu.kent.babelpages.rest.users.User;
import edu.kent.babelpages.rest.users.UsersDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BabelPagesApplication implements CommandLineRunner {
    private final UsersDAO usersDAO;
    private final PasswordEncoder passwordEncoder;

    public BabelPagesApplication(UsersDAO usersDAO, PasswordEncoder passwordEncoder) {
        this.usersDAO = usersDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(BabelPagesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // insert development admin user
        if(usersDAO.findByUsername("administrator") == null) {
            usersDAO.createUser(new User(null,
                    "administrator",
                    passwordEncoder.encode("10203040"),
                    "John",
                    "The Admin",
                    null,
                    "ADMIN"));
        }
    }
}
