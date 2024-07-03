package edu.kent.babelpages.lib.error.apiExceptions;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends ApiException {
    public ResourceAlreadyExistsException(HttpStatus status, String message) {
        super(status, message);
    }
}
