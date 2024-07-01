package edu.kent.babelpages.lib.error.apiExceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InvalidCredentialsException extends ApiException {
    public InvalidCredentialsException(HttpStatus status, String message) {
        super(status, message);
    }
}
