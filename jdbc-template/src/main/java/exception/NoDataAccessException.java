package exception;

public class NoDataAccessException extends RuntimeException {
    public NoDataAccessException() {
        super();
    }

    public NoDataAccessException(String message) {
        super(message);
    }

    public NoDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDataAccessException(Throwable cause) {
        super(cause);
    }
}
