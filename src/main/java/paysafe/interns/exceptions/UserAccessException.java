package paysafe.interns.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when JPA throws a validation error in our model. Used to hide the
 * stack trace from the client.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAccessException extends RuntimeException {
    public UserAccessException() {
    }

    public UserAccessException(String message) {
        super(message);
    }

    public UserAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAccessException(Throwable cause) {
        super(cause);
    }

}
