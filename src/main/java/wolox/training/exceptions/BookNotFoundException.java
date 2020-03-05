package wolox.training.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super(String.format(ErrorConstats.NOT_FOUND_MESSAGE, "book"));
    }
}
