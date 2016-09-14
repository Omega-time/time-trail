package paysafe.interns.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when JPA throws a validation error in our model. Used to hide the
 * stack trace from the client.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "File not found")
public class DocNotFoundException extends RuntimeException {
    public DocNotFoundException() {
    }

    public DocNotFoundException(String message) {
        super(message);
    }

    public DocNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocNotFoundException(Throwable cause) {
        super(cause);
    }
}