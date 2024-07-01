package edu.kent.babelpages.lib.error.apiExceptions;

import org.springframework.http.HttpStatus;

public class ResourceDoesNotExistException extends ApiException{
    public ResourceDoesNotExistException(HttpStatus status, String message) {
        super(status, message);
    }
}
