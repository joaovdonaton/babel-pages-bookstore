package edu.kent.babelpages.lib.error;

import edu.kent.babelpages.lib.error.apiExceptions.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        // extract individual validation errors and format them
        List<String> extractedErrors = ex.getAllErrors().stream().map(
                objectError -> "Field [" +((FieldError) objectError).getField() + "]: " + objectError.getDefaultMessage()
        ).toList();

        return new ResponseEntity<>(
                new ApiErrorDTO(ex.getStatusCode().value(), "Validation failed!",
                        extractedErrors,
                        Timestamp.valueOf(LocalDateTime.now()).toString(),
                        request.getContextPath()+request.getServletPath()),
                BAD_REQUEST);
    }

    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<ApiErrorDTO> handleAuthorizationDeniedException(AuthorizationDeniedException ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiErrorDTO(
                HttpStatus.FORBIDDEN.value(),
                "You are not authorized to access this endpoint.",
                null,
                Timestamp.valueOf(LocalDateTime.now()).toString(),
                request.getContextPath()+request.getServletPath()
        ), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiErrorDTO> handleApiExceptions(ApiException ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ApiErrorDTO(
                ex.getStatus().value(),
                ex.getMessage(),
                List.of(),
                Timestamp.valueOf(LocalDateTime.now()).toString(),
                request.getContextPath()+request.getServletPath())
        , ex.getStatus());
    }
}
