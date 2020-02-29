package wolox.training.exceptions;
import wolox.training.models.Book;

public class BookAlreadyOwnedException extends RuntimeException {
    public BookAlreadyOwnedException(Book book) {
        super("The book " + book.getTitle() + " is already in the user's collection");
    }
}
