package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Book Id mismatch")
public class BookIdMismatchException extends RuntimeException {
    public BookIdMismatchException () {
        super(String.format(ErrorConstats.ID_MISSMATCH_MESSAGE, "book"));
    }
}
