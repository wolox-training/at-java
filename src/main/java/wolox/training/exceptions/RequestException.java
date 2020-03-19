package wolox.training.exceptions;

public class RequestException extends RuntimeException {
    public RequestException(String msg) {
        super(String.format("%s. %s", ErrorConstats.REQUEST_EXCEPTION, msg));
    }

    public RequestException() {
        super(ErrorConstats.REQUEST_EXCEPTION);
    }
}
