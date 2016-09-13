package paysafe.interns.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when JPA throws a validation error in our model. Used to hide the
 * stack trace from the client.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid file passed for upload")
public class InvalidDocException extends RuntimeException {
    public InvalidDocException() {
    }

    public InvalidDocException(String message) {
        super(message);
    }

    public InvalidDocException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDocException(Throwable cause) {
        super(cause);
    }
}