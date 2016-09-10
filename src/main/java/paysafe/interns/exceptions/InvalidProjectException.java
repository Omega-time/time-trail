package paysafe.interns.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when JPA throws a validation error
 * in our model. Used to hide the stack trace from the client.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid values passed for project")
public class InvalidProjectException extends RuntimeException {
    public InvalidProjectException() {
    }

    public InvalidProjectException(String message) {
        super(message);
    }

    public InvalidProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidProjectException(Throwable cause) {
        super(cause);
    }
}
