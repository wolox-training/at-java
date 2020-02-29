package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already owns the book")
public class BookAlreadyOwnedException extends RuntimeException {
    public BookAlreadyOwnedException(String bookTitle) {
        super(String.format(ErrorConstats.BOOK_ALREADY_IN_COLLECTION_MESSAGE, bookTitle));
    }
}
