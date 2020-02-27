package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User Id mismatch")
public class UserIdMismatchException extends RuntimeException {
    public UserIdMismatchException() {
        super("The user id does not match");
    }
}
