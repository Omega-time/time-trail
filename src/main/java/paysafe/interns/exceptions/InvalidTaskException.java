package paysafe.interns.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when JPA throws a validation error in our model. Used to hide the
 * stack trace from the client.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid values passed for creating task")
public class InvalidTaskException extends RuntimeException {
	public InvalidTaskException() {
	}

	public InvalidTaskException(String message) {
		super(message);
	}

	public InvalidTaskException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTaskException(Throwable cause) {
		super(cause);
	}
}
