package wolox.training.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super(ErrorConstats.WRONG_CREDENTIALS_MESSAGE);
    }
}
