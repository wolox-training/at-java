package wolox.training.exceptions;

public class BookAlreadyOwnedException extends RuntimeException {

    public BookAlreadyOwnedException(String bookTitle) {
        super(String.format(ErrorConstats.BOOK_ALREADY_IN_COLLECTION, bookTitle));
    }
}
